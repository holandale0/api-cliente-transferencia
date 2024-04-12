package com.client.transferencia.domain.cliente.service;

import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import com.client.transferencia.infrastructure.data.exception.InfrastructureException;
import com.client.transferencia.infrastructure.data.integration.rest.cliente.ClienteIntegration;
import com.client.transferencia.infrastructure.shared.dto.ClienteDTO;
import com.client.transferencia.infrastructure.shared.dto.ContaOrigemDestinoDTO;
import com.client.transferencia.infrastructure.shared.dto.TipoPessoaEnumDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService = new ClienteServiceImpl();

    @Mock
    private ClienteIntegration clienteIntegration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarCliente_Success() {

        ClienteDTO clienteDTO = clienteMock();
        when(clienteIntegration.obterCliente(anyString())).thenReturn(clienteDTO);

        Mono<TransferenciaRequestDTO> resultMono = clienteService.buscarCliente(new TransferenciaRequestDTO("clienteId"));

        StepVerifier.create(resultMono)
                .expectNext(new TransferenciaRequestDTO("clienteId"))
                .verifyComplete();
    }

    @Test
    void buscarCliente_ClienteNaoEncontrado() {

        when(clienteIntegration.obterCliente(anyString())).thenReturn(null);

        Mono<TransferenciaRequestDTO> resultMono = clienteService.buscarCliente(new TransferenciaRequestDTO("clienteId"));

        StepVerifier.create(resultMono)
                .expectError(InfrastructureException.class)
                .verify();
    }

    private Mono<TransferenciaRequestDTO> mockObject() {
        return Mono.create(monoSink -> {
            TransferenciaRequestDTO.builder()
                    .idCliente("2ceb26e9-7b5c-417e-bf75-ffaa66e3a76f")
                    .valor(BigDecimal.TEN)
                    .contaDTO(ContaOrigemDestinoDTO.builder()
                            .idDestino("41313d7b-bd75-4c75-9dea-1f4be434007f")
                            .idOrigem("d0d32142-74b7-4aca-9c68-838aeacef96b")
                            .build())
                    .build();
        });
    }


    private ClienteDTO clienteMock() {
        return ClienteDTO.builder()
                .id("bcdd1048-a501-4608-bc82-66d7b4db3600")
                .nome("João Silva")
                .telefone("912348765")
                .tipoPessoa(TipoPessoaEnumDTO.PESSOA_FISICA)
                .build();
    }


}