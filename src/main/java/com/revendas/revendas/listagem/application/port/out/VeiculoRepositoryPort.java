package com.revendas.revendas.listagem.application.port.out;

import com.revendas.revendas.listagem.domain.model.Veiculo;

import java.util.List;
import java.util.Optional;

public interface VeiculoRepositoryPort {

    Veiculo salvar(Veiculo veiculo);

    Optional<Veiculo> buscarPorId(Long id);

    List<Veiculo> listarTodos();

    Veiculo atualizar(Long id, Veiculo veiculo);
}
