package com.revendas.revendas.listagem.domain.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class StatusVeiculoTest {

    @Test
    void deveConterTodosOsValores() {
        assertThat(StatusVeiculo.values()).containsExactlyInAnyOrder(
                StatusVeiculo.DISPONIVEL,
                StatusVeiculo.RESERVADO,
                StatusVeiculo.VENDIDO
        );
    }

    @Test
    void deveResolverPorNome() {
        assertThat(StatusVeiculo.valueOf("DISPONIVEL")).isEqualTo(StatusVeiculo.DISPONIVEL);
        assertThat(StatusVeiculo.valueOf("RESERVADO")).isEqualTo(StatusVeiculo.RESERVADO);
        assertThat(StatusVeiculo.valueOf("VENDIDO")).isEqualTo(StatusVeiculo.VENDIDO);
    }
}
