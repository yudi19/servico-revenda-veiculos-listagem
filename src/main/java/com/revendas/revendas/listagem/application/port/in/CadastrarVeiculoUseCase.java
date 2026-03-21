package com.revendas.revendas.listagem.application.port.in;

import com.revendas.revendas.listagem.domain.model.Veiculo;

public interface CadastrarVeiculoUseCase {

    Veiculo cadastrar(Veiculo veiculo);
}
