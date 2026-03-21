package com.revendas.revendas.listagem.application.service;

import com.revendas.revendas.listagem.application.port.out.VeiculoRepositoryPort;
import com.revendas.revendas.listagem.domain.exception.VeiculoNaoEncontradoException;
import com.revendas.revendas.listagem.domain.model.StatusVeiculo;
import com.revendas.revendas.listagem.domain.model.Veiculo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceTest {

    @Mock
    private VeiculoRepositoryPort repositoryPort;

    @InjectMocks
    private VeiculoService service;

    private Veiculo veiculoBase() {
        return Veiculo.builder()
                .id(1L).marca("Toyota").modelo("Corolla").ano(2023)
                .cor("Branco").preco(new BigDecimal("120000.00"))
                .placa("ABC-1234").status(StatusVeiculo.DISPONIVEL)
                .build();
    }

    // --- cadastrar ---

    @Test
    void cadastrar_deveSalvarERetornarVeiculo() {
        Veiculo veiculo = veiculoBase();
        when(repositoryPort.salvar(veiculo)).thenReturn(veiculo);

        Veiculo result = service.cadastrar(veiculo);

        assertThat(result).isEqualTo(veiculo);
        verify(repositoryPort).salvar(veiculo);
    }

    // --- editar ---

    @Test
    void editar_deveAtualizarQuandoVeiculoExiste() {
        Veiculo veiculo = veiculoBase();
        when(repositoryPort.buscarPorId(1L)).thenReturn(Optional.of(veiculo));
        when(repositoryPort.atualizar(eq(1L), any())).thenReturn(veiculo);

        Veiculo result = service.editar(1L, veiculo);

        assertThat(result).isEqualTo(veiculo);
        verify(repositoryPort).buscarPorId(1L);
        verify(repositoryPort).atualizar(1L, veiculo);
    }

    @Test
    void editar_deveLancarExcecaoQuandoVeiculoNaoExiste() {
        when(repositoryPort.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.editar(99L, veiculoBase()))
                .isInstanceOf(VeiculoNaoEncontradoException.class)
                .hasMessageContaining("99");

        verify(repositoryPort, never()).atualizar(any(), any());
    }

    // --- listar ---

    @Test
    void listar_deveRetornarListaDoRepositorio() {
        List<Veiculo> lista = List.of(veiculoBase());
        when(repositoryPort.listarTodos()).thenReturn(lista);

        List<Veiculo> result = service.listar();

        assertThat(result).hasSize(1).containsExactlyElementsOf(lista);
        verify(repositoryPort).listarTodos();
    }

    @Test
    void listar_deveRetornarListaVazia() {
        when(repositoryPort.listarTodos()).thenReturn(List.of());

        assertThat(service.listar()).isEmpty();
    }

    // --- buscar ---

    @Test
    void buscar_deveRetornarVeiculoQuandoExiste() {
        Veiculo veiculo = veiculoBase();
        when(repositoryPort.buscarPorId(1L)).thenReturn(Optional.of(veiculo));

        Veiculo result = service.buscar(1L);

        assertThat(result).isEqualTo(veiculo);
    }

    @Test
    void buscar_deveLancarExcecaoQuandoNaoExiste() {
        when(repositoryPort.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscar(99L))
                .isInstanceOf(VeiculoNaoEncontradoException.class)
                .hasMessageContaining("99");
    }
}
