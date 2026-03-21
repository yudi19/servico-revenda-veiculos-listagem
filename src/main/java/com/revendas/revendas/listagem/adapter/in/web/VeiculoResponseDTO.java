package com.revendas.revendas.listagem.adapter.in.web;

import com.revendas.revendas.listagem.domain.model.StatusVeiculo;

import java.math.BigDecimal;

public record VeiculoResponseDTO(
        Long id,
        String marca,
        String modelo,
        Integer ano,
        String cor,
        BigDecimal preco,
        String placa,
        StatusVeiculo status
) {}
