package com.revendas.revendas.listagem.adapter.out.persistence;

import com.revendas.revendas.listagem.domain.model.StatusVeiculo;
import com.revendas.revendas.listagem.domain.model.Veiculo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class VeiculoPersistenceMapperTest {

    private Veiculo veiculo() {
        return Veiculo.builder()
                .id(1L).marca("Toyota").modelo("Corolla").ano(2023)
                .cor("Branco").preco(new BigDecimal("120000.00"))
                .placa("ABC-1234").status(StatusVeiculo.DISPONIVEL)
                .build();
    }

    private VeiculoJpaEntity entity() {
        return VeiculoJpaEntity.builder()
                .id(1L).marca("Toyota").modelo("Corolla").ano(2023)
                .cor("Branco").preco(new BigDecimal("120000.00"))
                .placa("ABC-1234").status(StatusVeiculo.DISPONIVEL)
                .build();
    }

    @Test
    void toDomain_deveMapearTodosOsCampos() {
        Veiculo result = VeiculoPersistenceMapper.toDomain(entity());

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getMarca()).isEqualTo("Toyota");
        assertThat(result.getModelo()).isEqualTo("Corolla");
        assertThat(result.getAno()).isEqualTo(2023);
        assertThat(result.getCor()).isEqualTo("Branco");
        assertThat(result.getPreco()).isEqualByComparingTo("120000.00");
        assertThat(result.getPlaca()).isEqualTo("ABC-1234");
        assertThat(result.getStatus()).isEqualTo(StatusVeiculo.DISPONIVEL);
    }

    @Test
    void toEntity_deveMapearTodosOsCampos() {
        VeiculoJpaEntity result = VeiculoPersistenceMapper.toEntity(veiculo());

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getMarca()).isEqualTo("Toyota");
        assertThat(result.getModelo()).isEqualTo("Corolla");
        assertThat(result.getAno()).isEqualTo(2023);
        assertThat(result.getCor()).isEqualTo("Branco");
        assertThat(result.getPreco()).isEqualByComparingTo("120000.00");
        assertThat(result.getPlaca()).isEqualTo("ABC-1234");
        assertThat(result.getStatus()).isEqualTo(StatusVeiculo.DISPONIVEL);
    }
}
