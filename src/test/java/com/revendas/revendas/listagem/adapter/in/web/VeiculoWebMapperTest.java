package com.revendas.revendas.listagem.adapter.in.web;

import com.revendas.revendas.listagem.domain.model.StatusVeiculo;
import com.revendas.revendas.listagem.domain.model.Veiculo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class VeiculoWebMapperTest {

    @Test
    void toDomain_deveMapearDTOParaDomain() {
        VeiculoRequestDTO dto = new VeiculoRequestDTO(
                "Toyota", "Corolla", 2023, "Branco",
                new BigDecimal("120000.00"), "ABC-1234", StatusVeiculo.DISPONIVEL);

        Veiculo result = VeiculoWebMapper.toDomain(dto);

        assertThat(result.getMarca()).isEqualTo("Toyota");
        assertThat(result.getModelo()).isEqualTo("Corolla");
        assertThat(result.getAno()).isEqualTo(2023);
        assertThat(result.getCor()).isEqualTo("Branco");
        assertThat(result.getPreco()).isEqualByComparingTo("120000.00");
        assertThat(result.getPlaca()).isEqualTo("ABC-1234");
        assertThat(result.getStatus()).isEqualTo(StatusVeiculo.DISPONIVEL);
        assertThat(result.getId()).isNull();
    }

    @Test
    void toResponse_deveMapearDomainParaDTO() {
        Veiculo veiculo = Veiculo.builder()
                .id(1L).marca("Toyota").modelo("Corolla").ano(2023)
                .cor("Branco").preco(new BigDecimal("120000.00"))
                .placa("ABC-1234").status(StatusVeiculo.DISPONIVEL)
                .build();

        VeiculoResponseDTO result = VeiculoWebMapper.toResponse(veiculo);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.marca()).isEqualTo("Toyota");
        assertThat(result.modelo()).isEqualTo("Corolla");
        assertThat(result.ano()).isEqualTo(2023);
        assertThat(result.cor()).isEqualTo("Branco");
        assertThat(result.preco()).isEqualByComparingTo("120000.00");
        assertThat(result.placa()).isEqualTo("ABC-1234");
        assertThat(result.status()).isEqualTo(StatusVeiculo.DISPONIVEL);
    }
}
