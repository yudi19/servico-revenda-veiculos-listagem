package com.revendas.revendas.listagem.adapter.out.persistence;

import com.revendas.revendas.listagem.domain.model.StatusVeiculo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;

class VeiculoJpaEntityTest {

    private VeiculoJpaEntity entity() {
        return VeiculoJpaEntity.builder()
                .id(1L).marca("Toyota").modelo("Corolla").ano(2023)
                .cor("Branco").preco(new BigDecimal("120000.00"))
                .placa("ABC-1234").status(StatusVeiculo.DISPONIVEL)
                .build();
    }

    @Test
    void deveBuildarComTodosOsCampos() {
        VeiculoJpaEntity e = entity();
        assertThat(e.getId()).isEqualTo(1L);
        assertThat(e.getMarca()).isEqualTo("Toyota");
        assertThat(e.getModelo()).isEqualTo("Corolla");
        assertThat(e.getAno()).isEqualTo(2023);
        assertThat(e.getCor()).isEqualTo("Branco");
        assertThat(e.getPreco()).isEqualByComparingTo("120000.00");
        assertThat(e.getPlaca()).isEqualTo("ABC-1234");
        assertThat(e.getStatus()).isEqualTo(StatusVeiculo.DISPONIVEL);
    }

    @Test
    void devePermitirSetters() {
        VeiculoJpaEntity e = new VeiculoJpaEntity();
        e.setId(2L);
        e.setMarca("Honda");
        e.setModelo("Civic");
        e.setAno(2022);
        e.setCor("Preto");
        e.setPreco(new BigDecimal("90000.00"));
        e.setPlaca("XYZ-9999");
        e.setStatus(StatusVeiculo.VENDIDO);

        assertThat(e.getMarca()).isEqualTo("Honda");
        assertThat(e.getStatus()).isEqualTo(StatusVeiculo.VENDIDO);
    }

    @Test
    void deveTerEqualsEHashCode() {
        VeiculoJpaEntity e1 = entity();
        VeiculoJpaEntity e2 = entity();
        assertThat(e1).isEqualTo(e2);
        assertThat(e1.hashCode()).isEqualTo(e2.hashCode());
    }

    @Test
    void deveTerToString() {
        assertThat(entity().toString()).contains("Toyota");
    }

    @Test
    void deveInstanciarComConstrutorCompletoArgs() {
        VeiculoJpaEntity e = new VeiculoJpaEntity(1L, "Ford", "Ka", 2021, "Vermelho",
                new BigDecimal("55000"), "GHI-3456", StatusVeiculo.RESERVADO);
        assertThat(e.getStatus()).isEqualTo(StatusVeiculo.RESERVADO);
    }

    @Test
    void privateConstructorDoMapper_devePodeSerInstanciado() throws Exception {
        Constructor<VeiculoPersistenceMapper> c =
                VeiculoPersistenceMapper.class.getDeclaredConstructor();
        c.setAccessible(true);
        assertThat(c.newInstance()).isNotNull();
    }
}
