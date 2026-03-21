package com.revendas.revendas.listagem.adapter.in.web;

import com.revendas.revendas.listagem.application.port.in.BuscarVeiculoUseCase;
import com.revendas.revendas.listagem.application.port.in.CadastrarVeiculoUseCase;
import com.revendas.revendas.listagem.application.port.in.EditarVeiculoUseCase;
import com.revendas.revendas.listagem.application.port.in.ListarVeiculosUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final CadastrarVeiculoUseCase cadastrarVeiculoUseCase;
    private final EditarVeiculoUseCase editarVeiculoUseCase;
    private final ListarVeiculosUseCase listarVeiculosUseCase;
    private final BuscarVeiculoUseCase buscarVeiculoUseCase;

    @PostMapping
    public ResponseEntity<VeiculoResponseDTO> cadastrar(@Valid @RequestBody VeiculoRequestDTO dto) {
        var veiculo = cadastrarVeiculoUseCase.cadastrar(VeiculoWebMapper.toDomain(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(VeiculoWebMapper.toResponse(veiculo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> editar(
            @PathVariable Long id,
            @Valid @RequestBody VeiculoRequestDTO dto) {
        var veiculo = editarVeiculoUseCase.editar(id, VeiculoWebMapper.toDomain(dto));
        return ResponseEntity.ok(VeiculoWebMapper.toResponse(veiculo));
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponseDTO>> listar() {
        List<VeiculoResponseDTO> veiculos = listarVeiculosUseCase.listar()
                .stream()
                .map(VeiculoWebMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(veiculos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(VeiculoWebMapper.toResponse(buscarVeiculoUseCase.buscar(id)));
    }
}
