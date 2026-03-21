package com.revendas.revendas.listagem.application.port.in;

import com.revendas.revendas.listagem.domain.model.Veiculo;

import java.util.List;

public interface ListarVeiculosUseCase {

    List<Veiculo> listar();
}
