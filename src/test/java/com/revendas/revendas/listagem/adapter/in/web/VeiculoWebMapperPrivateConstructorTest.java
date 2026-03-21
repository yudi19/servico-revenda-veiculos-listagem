package com.revendas.revendas.listagem.adapter.in.web;

import com.revendas.revendas.listagem.domain.model.StatusVeiculo;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class VeiculoWebMapperPrivateConstructorTest {

    @Test
    void privateConstructor_devePodeSerInstanciado() throws Exception {
        Constructor<VeiculoWebMapper> c = VeiculoWebMapper.class.getDeclaredConstructor();
        c.setAccessible(true);
        assertThat(c.newInstance()).isNotNull();
    }

    @Test
    void requestDTO_deveExporsAcessores() {
        VeiculoRequestDTO dto = new VeiculoRequestDTO(
                "Toyota", "Corolla", 2023, "Branco",
                new BigDecimal("120000"), "ABC-1234", StatusVeiculo.DISPONIVEL);
        assertThat(dto.marca()).isEqualTo("Toyota");
        assertThat(dto.modelo()).isEqualTo("Corolla");
        assertThat(dto.ano()).isEqualTo(2023);
        assertThat(dto.cor()).isEqualTo("Branco");
        assertThat(dto.preco()).isEqualByComparingTo("120000");
        assertThat(dto.placa()).isEqualTo("ABC-1234");
        assertThat(dto.status()).isEqualTo(StatusVeiculo.DISPONIVEL);
    }

    @Test
    void responseDTO_deveExporsAcessores() {
        VeiculoResponseDTO dto = new VeiculoResponseDTO(
                1L, "Toyota", "Corolla", 2023, "Branco",
                new BigDecimal("120000"), "ABC-1234", StatusVeiculo.DISPONIVEL);
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.status()).isEqualTo(StatusVeiculo.DISPONIVEL);
    }
}
