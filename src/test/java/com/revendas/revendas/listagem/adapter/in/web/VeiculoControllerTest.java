package com.revendas.revendas.listagem.adapter.in.web;

import com.revendas.revendas.listagem.application.port.in.BuscarVeiculoUseCase;
import com.revendas.revendas.listagem.application.port.in.CadastrarVeiculoUseCase;
import com.revendas.revendas.listagem.application.port.in.EditarVeiculoUseCase;
import com.revendas.revendas.listagem.application.port.in.ListarVeiculosUseCase;
import com.revendas.revendas.listagem.domain.exception.VeiculoNaoEncontradoException;
import com.revendas.revendas.listagem.domain.model.StatusVeiculo;
import com.revendas.revendas.listagem.domain.model.Veiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VeiculoControllerTest {

    @Mock private CadastrarVeiculoUseCase cadastrarVeiculoUseCase;
    @Mock private EditarVeiculoUseCase editarVeiculoUseCase;
    @Mock private ListarVeiculosUseCase listarVeiculosUseCase;
    @Mock private BuscarVeiculoUseCase buscarVeiculoUseCase;

    @InjectMocks
    private VeiculoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    private Veiculo veiculoBase() {
        return Veiculo.builder()
                .id(1L).marca("Toyota").modelo("Corolla").ano(2023)
                .cor("Branco").preco(new BigDecimal("120000.00"))
                .placa("ABC-1234").status(StatusVeiculo.DISPONIVEL)
                .build();
    }

    @Test
    void cadastrar_deveRetornar201ComVeiculo() throws Exception {
        when(cadastrarVeiculoUseCase.cadastrar(any())).thenReturn(veiculoBase());

        String json = "{" +
                "\"marca\":\"Toyota\"," +
                "\"modelo\":\"Corolla\"," +
                "\"ano\":2023," +
                "\"cor\":\"Branco\"," +
                "\"preco\":120000.00," +
                "\"placa\":\"ABC-1234\"," +
                "\"status\":\"DISPONIVEL\"}";

        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.marca").value("Toyota"))
                .andExpect(jsonPath("$.status").value("DISPONIVEL"));
    }

    @Test
    void cadastrar_deveRetornar400QuandoCamposNulos() throws Exception {
        String json = "{\"marca\":null,\"modelo\":null,\"ano\":null," +
                      "\"cor\":null,\"preco\":null,\"placa\":null,\"status\":null}";

        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void editar_deveRetornar200ComVeiculoAtualizado() throws Exception {
        when(editarVeiculoUseCase.editar(eq(1L), any())).thenReturn(veiculoBase());

        String json = "{" +
                "\"marca\":\"Toyota\"," +
                "\"modelo\":\"Corolla\"," +
                "\"ano\":2023," +
                "\"cor\":\"Branco\"," +
                "\"preco\":120000.00," +
                "\"placa\":\"ABC-1234\"," +
                "\"status\":\"DISPONIVEL\"}";

        mockMvc.perform(put("/veiculos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.marca").value("Toyota"));
    }

    @Test
    void editar_deveRetornar404QuandoNaoExiste() throws Exception {
        when(editarVeiculoUseCase.editar(eq(99L), any()))
                .thenThrow(new VeiculoNaoEncontradoException(99L));

        String json = "{" +
                "\"marca\":\"Toyota\"," +
                "\"modelo\":\"Corolla\"," +
                "\"ano\":2023," +
                "\"cor\":\"Branco\"," +
                "\"preco\":120000.00," +
                "\"placa\":\"ABC-1234\"," +
                "\"status\":\"DISPONIVEL\"}";

        mockMvc.perform(put("/veiculos/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void listar_deveRetornar200ComLista() throws Exception {
        when(listarVeiculosUseCase.listar()).thenReturn(List.of(veiculoBase()));

        mockMvc.perform(get("/veiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].marca").value("Toyota"))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void listar_deveRetornar200ComListaVazia() throws Exception {
        when(listarVeiculosUseCase.listar()).thenReturn(List.of());

        mockMvc.perform(get("/veiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void buscar_deveRetornar200QuandoExiste() throws Exception {
        when(buscarVeiculoUseCase.buscar(1L)).thenReturn(veiculoBase());

        mockMvc.perform(get("/veiculos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.marca").value("Toyota"));
    }

    @Test
    void buscar_deveRetornar404QuandoNaoExiste() throws Exception {
        when(buscarVeiculoUseCase.buscar(99L))
                .thenThrow(new VeiculoNaoEncontradoException(99L));

        mockMvc.perform(get("/veiculos/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.mensagem").value("Ve\u00edculo n\u00e3o encontrado com id: 99"));
    }
}