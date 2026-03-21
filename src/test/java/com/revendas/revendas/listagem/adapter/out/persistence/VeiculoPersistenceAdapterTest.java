package com.revendas.revendas.listagem.adapter.out.persistence;

import com.revendas.revendas.listagem.domain.exception.VeiculoNaoEncontradoException;
import com.revendas.revendas.listagem.domain.model.StatusVeiculo;
import com.revendas.revendas.listagem.domain.model.Veiculo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoPersistenceAdapterTest {

    @Mock
    private VeiculoJpaRepository jpaRepository;

    @InjectMocks
    private VeiculoPersistenceAdapter adapter;

    private VeiculoJpaEntity entityBase() {
        return VeiculoJpaEntity.builder()
                .id(1L).marca("Toyota").modelo("Corolla").ano(2023)
                .cor("Branco").preco(new BigDecimal("120000.00"))
                .placa("ABC-1234").status(StatusVeiculo.DISPONIVEL)
                .build();
    }

    private Veiculo veiculoBase() {
        return Veiculo.builder()
                .id(1L).marca("Toyota").modelo("Corolla").ano(2023)
                .cor("Branco").preco(new BigDecimal("120000.00"))
                .placa("ABC-1234").status(StatusVeiculo.DISPONIVEL)
                .build();
    }

    @Test
    void salvar_deveRetornarDomainMapeado() {
        VeiculoJpaEntity entity = entityBase();
        when(jpaRepository.save(any())).thenReturn(entity);

        Veiculo result = adapter.salvar(veiculoBase());

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getMarca()).isEqualTo("Toyota");
        verify(jpaRepository).save(any());
    }

    @Test
    void buscarPorId_deveRetornarOptionalComVeiculo() {
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entityBase()));

        Optional<Veiculo> result = adapter.buscarPorId(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
    }

    @Test
    void buscarPorId_deveRetornarOptionalVazioQuandoNaoExiste() {
        when(jpaRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Veiculo> result = adapter.buscarPorId(99L);

        assertThat(result).isEmpty();
    }

    @Test
    void listarTodos_deveRetornarListaOrdenada() {
        when(jpaRepository.findAll(Sort.by(Sort.Direction.ASC, "preco")))
                .thenReturn(List.of(entityBase()));

        List<Veiculo> result = adapter.listarTodos();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMarca()).isEqualTo("Toyota");
    }

    @Test
    void atualizar_deveAtualizarCamposERetornarDomain() {
        VeiculoJpaEntity existing = entityBase();
        Veiculo novosDados = Veiculo.builder()
                .marca("Honda").modelo("HR-V").ano(2024)
                .cor("Prata").preco(new BigDecimal("90000.00"))
                .placa("XYZ-9999").status(StatusVeiculo.RESERVADO)
                .build();

        when(jpaRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(jpaRepository.save(existing)).thenReturn(existing);

        Veiculo result = adapter.atualizar(1L, novosDados);

        assertThat(result).isNotNull();
        verify(jpaRepository).save(existing);
        assertThat(existing.getMarca()).isEqualTo("Honda");
        assertThat(existing.getModelo()).isEqualTo("HR-V");
        assertThat(existing.getStatus()).isEqualTo(StatusVeiculo.RESERVADO);
    }

    @Test
    void atualizar_deveLancarExcecaoQuandoNaoExiste() {
        when(jpaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adapter.atualizar(99L, veiculoBase()))
                .isInstanceOf(VeiculoNaoEncontradoException.class)
                .hasMessageContaining("99");
    }
}
