package com.revendas.revendas.listagem.adapter.out.persistence;

import com.revendas.revendas.listagem.domain.model.Veiculo;

public class VeiculoPersistenceMapper {

    private VeiculoPersistenceMapper() {}

    public static Veiculo toDomain(VeiculoJpaEntity entity) {
        return Veiculo.builder()
                .id(entity.getId())
                .marca(entity.getMarca())
                .modelo(entity.getModelo())
                .ano(entity.getAno())
                .cor(entity.getCor())
                .preco(entity.getPreco())
                .placa(entity.getPlaca())
                .status(entity.getStatus())
                .build();
    }

    public static VeiculoJpaEntity toEntity(Veiculo veiculo) {
        return VeiculoJpaEntity.builder()
                .id(veiculo.getId())
                .marca(veiculo.getMarca())
                .modelo(veiculo.getModelo())
                .ano(veiculo.getAno())
                .cor(veiculo.getCor())
                .preco(veiculo.getPreco())
                .placa(veiculo.getPlaca())
                .status(veiculo.getStatus())
                .build();
    }
}
