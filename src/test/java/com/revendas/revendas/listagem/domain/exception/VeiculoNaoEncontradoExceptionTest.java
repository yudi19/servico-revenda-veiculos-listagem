package com.revendas.revendas.listagem.domain.exception;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class VeiculoNaoEncontradoExceptionTest {

    @Test
    void deveCriarMensagemComId() {
        var ex = new VeiculoNaoEncontradoException(42L);
        assertThat(ex.getMessage()).isEqualTo("Veículo não encontrado com id: 42");
    }

    @Test
    void deveSerRuntimeException() {
        assertThat(new VeiculoNaoEncontradoException(1L)).isInstanceOf(RuntimeException.class);
    }
}
