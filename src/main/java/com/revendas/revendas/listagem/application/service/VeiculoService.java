package com.revendas.revendas.listagem.application.service;

import com.revendas.revendas.listagem.application.port.in.BuscarVeiculoUseCase;
import com.revendas.revendas.listagem.application.port.in.CadastrarVeiculoUseCase;
import com.revendas.revendas.listagem.application.port.in.EditarVeiculoUseCase;
import com.revendas.revendas.listagem.application.port.in.ListarVeiculosUseCase;
import com.revendas.revendas.listagem.application.port.out.VeiculoRepositoryPort;
import com.revendas.revendas.listagem.domain.exception.VeiculoNaoEncontradoException;
import com.revendas.revendas.listagem.domain.model.Veiculo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService implements
        CadastrarVeiculoUseCase,
        EditarVeiculoUseCase,
        ListarVeiculosUseCase,
        BuscarVeiculoUseCase {

    private final VeiculoRepositoryPort repositoryPort;

    @Override
    public Veiculo cadastrar(Veiculo veiculo) {
        return repositoryPort.salvar(veiculo);
    }

    @Override
    public Veiculo editar(Long id, Veiculo veiculo) {
        repositoryPort.buscarPorId(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(id));
        return repositoryPort.atualizar(id, veiculo);
    }

    @Override
    public List<Veiculo> listar() {
        return repositoryPort.listarTodos();
    }

    @Override
    public Veiculo buscar(Long id) {
        return repositoryPort.buscarPorId(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(id));
    }
}
