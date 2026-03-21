package com.revendas.revendas.listagem.adapter.in.web;

import com.revendas.revendas.listagem.domain.model.Veiculo;

public class VeiculoWebMapper {

    private VeiculoWebMapper() {}

    public static Veiculo toDomain(VeiculoRequestDTO dto) {
        return Veiculo.builder()
                .marca(dto.marca())
                .modelo(dto.modelo())
                .ano(dto.ano())
                .cor(dto.cor())
                .preco(dto.preco())
                .placa(dto.placa())
                .status(dto.status())
                .build();
    }

    public static VeiculoResponseDTO toResponse(Veiculo veiculo) {
        return new VeiculoResponseDTO(
                veiculo.getId(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getAno(),
                veiculo.getCor(),
                veiculo.getPreco(),
                veiculo.getPlaca(),
                veiculo.getStatus()
        );
    }
}
