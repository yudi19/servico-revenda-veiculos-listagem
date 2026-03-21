package com.revendas.revendas.listagem.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

class VeiculoTest {

    @Test
    void deveBuildarVeiculoComTodosOsCampos() {
        Veiculo v = Veiculo.builder()
                .id(1L).marca("Toyota").modelo("Corolla").ano(2023)
                .cor("Branco").preco(new BigDecimal("120000.00"))
                .placa("ABC-1234").status(StatusVeiculo.DISPONIVEL)
                .build();

        assertThat(v.getId()).isEqualTo(1L);
        assertThat(v.getMarca()).isEqualTo("Toyota");
        assertThat(v.getModelo()).isEqualTo("Corolla");
        assertThat(v.getAno()).isEqualTo(2023);
        assertThat(v.getCor()).isEqualTo("Branco");
        assertThat(v.getPreco()).isEqualByComparingTo("120000.00");
        assertThat(v.getPlaca()).isEqualTo("ABC-1234");
        assertThat(v.getStatus()).isEqualTo(StatusVeiculo.DISPONIVEL);
    }

    @Test
    void devePermitirSetters() {
        Veiculo v = new Veiculo();
        v.setId(2L);
        v.setMarca("Honda");
        v.setModelo("Civic");
        v.setAno(2022);
        v.setCor("Preto");
        v.setPreco(new BigDecimal("90000.00"));
        v.setPlaca("XYZ-9999");
        v.setStatus(StatusVeiculo.VENDIDO);

        assertThat(v.getId()).isEqualTo(2L);
        assertThat(v.getMarca()).isEqualTo("Honda");
        assertThat(v.getModelo()).isEqualTo("Civic");
        assertThat(v.getAno()).isEqualTo(2022);
        assertThat(v.getCor()).isEqualTo("Preto");
        assertThat(v.getPreco()).isEqualByComparingTo("90000.00");
        assertThat(v.getPlaca()).isEqualTo("XYZ-9999");
        assertThat(v.getStatus()).isEqualTo(StatusVeiculo.VENDIDO);
    }

    @Test
    void deveTerEqualsEHashCode() {
        Veiculo v1 = Veiculo.builder().id(1L).marca("Ford").build();
        Veiculo v2 = Veiculo.builder().id(1L).marca("Ford").build();
        assertThat(v1).isEqualTo(v2);
        assertThat(v1.hashCode()).isEqualTo(v2.hashCode());
    }

    @Test
    void deveTerToString() {
        Veiculo v = Veiculo.builder().id(1L).marca("Fiat").build();
        assertThat(v.toString()).contains("Fiat");
    }

    @Test
    void deveInstanciarComConstrutorCompletoArgs() {
        Veiculo v = new Veiculo(1L, "Toyota", "Corolla", 2023, "Prata",
                new BigDecimal("100000"), "DEF-5678", StatusVeiculo.RESERVADO);
        assertThat(v.getStatus()).isEqualTo(StatusVeiculo.RESERVADO);
    }
}
