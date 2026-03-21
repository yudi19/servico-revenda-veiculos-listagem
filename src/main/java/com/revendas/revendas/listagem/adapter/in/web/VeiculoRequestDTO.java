package com.revendas.revendas.listagem.adapter.in.web;

import com.revendas.revendas.listagem.domain.model.StatusVeiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record VeiculoRequestDTO(

        @NotBlank(message = "Marca é obrigatória")
        String marca,

        @NotBlank(message = "Modelo é obrigatório")
        String modelo,

        @NotNull(message = "Ano é obrigatório")
        Integer ano,

        @NotBlank(message = "Cor é obrigatória")
        String cor,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser positivo")
        BigDecimal preco,

        @NotBlank(message = "Placa é obrigatória")
        String placa,

        @NotNull(message = "Status é obrigatório")
        StatusVeiculo status
) {}
