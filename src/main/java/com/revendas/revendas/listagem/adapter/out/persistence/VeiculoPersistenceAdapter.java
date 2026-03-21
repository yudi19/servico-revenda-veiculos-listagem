package com.revendas.revendas.listagem.adapter.out.persistence;

import com.revendas.revendas.listagem.application.port.out.VeiculoRepositoryPort;
import com.revendas.revendas.listagem.domain.exception.VeiculoNaoEncontradoException;
import com.revendas.revendas.listagem.domain.model.Veiculo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VeiculoPersistenceAdapter implements VeiculoRepositoryPort {

    private final VeiculoJpaRepository jpaRepository;

    @Override
    public Veiculo salvar(Veiculo veiculo) {
        VeiculoJpaEntity entity = VeiculoPersistenceMapper.toEntity(veiculo);
        return VeiculoPersistenceMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Veiculo> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(VeiculoPersistenceMapper::toDomain);
    }

    @Override
    public List<Veiculo> listarTodos() {
        return jpaRepository.findAll(Sort.by(Sort.Direction.ASC, "preco"))
                .stream()
                .map(VeiculoPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Veiculo atualizar(Long id, Veiculo veiculo) {
        VeiculoJpaEntity existing = jpaRepository.findById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(id));

        existing.setMarca(veiculo.getMarca());
        existing.setModelo(veiculo.getModelo());
        existing.setAno(veiculo.getAno());
        existing.setCor(veiculo.getCor());
        existing.setPreco(veiculo.getPreco());
        existing.setPlaca(veiculo.getPlaca());
        existing.setStatus(veiculo.getStatus());

        return VeiculoPersistenceMapper.toDomain(jpaRepository.save(existing));
    }
}
