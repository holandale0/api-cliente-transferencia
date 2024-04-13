package com.client.transferencia.application.service;


import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import com.client.transferencia.application.service.dto.TransferenciaResponseDTO;
import com.client.transferencia.domain.bacen.service.BacenService;
import com.client.transferencia.domain.cliente.service.ClienteService;
import com.client.transferencia.domain.conta.service.ContaService;
import com.client.transferencia.domain.exception.ClienteNotFoundException;
import com.client.transferencia.domain.exception.ContaNotFoundException;
import com.client.transferencia.infrastructure.shared.dto.ContaOrigemDestinoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class TransferenciaApplicationServiceTest {

    @InjectMocks
    private final TransferenciaApplicationService transferenciaApplicationService = new TransferenciaApplicationServiceImpl();

    @Mock
    private BacenService bacenService;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ContaService contaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testeEfetuarTransferenciaOk() {
        TransferenciaRequestDTO requestDTO = mockRequest();
        TransferenciaResponseDTO responseDTO = TransferenciaResponseDTO.builder()
                .idTransferencia("2ceb26e9-7b5c-417e-bf75-ffaa66e3a76f")
                .build();

        when(clienteService.buscarCliente(any())).thenReturn(Mono.just(requestDTO));
        when(contaService.buscarConta(any())).thenReturn(Mono.just(requestDTO));
        when(contaService.atualizarSaldo(any())).thenReturn(Mono.just(requestDTO));
        when(bacenService.notificarBacen(any())).thenReturn(Mono.just(responseDTO));

        Mono<TransferenciaResponseDTO> responseMono = transferenciaApplicationService.realizarTransferencia(requestDTO);

        StepVerifier.create(responseMono)
                .expectNext(responseDTO)
                .verifyComplete();

        verify(clienteService).buscarCliente(any());
        verify(contaService).buscarConta(any());
        verify(contaService).atualizarSaldo(any());
        verify(bacenService).notificarBacen(any());
    }

    @Test
    public void testeEfetuarTransferenciaFalhaAoBuscarCliente() {
        TransferenciaRequestDTO requestDTO = mockRequest();

        when(clienteService.buscarCliente(any())).thenReturn(Mono.error(new ClienteNotFoundException("Erro ao buscar cliente")));

        Mono<TransferenciaResponseDTO> responseMono = transferenciaApplicationService.realizarTransferencia(requestDTO);

        StepVerifier.create(responseMono)
                .expectError(ClienteNotFoundException.class)
                .verify();

        verify(clienteService).buscarCliente(any());
        verifyNoInteractions(contaService, bacenService);
    }

    @Test
    public void testeEfetuarTransferenciaFalhaAoBuscarConta() {
        TransferenciaRequestDTO requestDTO = mockRequest();

        when(clienteService.buscarCliente(any())).thenReturn(Mono.just(requestDTO));
        when(contaService.buscarConta(any())).thenReturn(Mono.error(new ContaNotFoundException("Erro ao buscar conta")));

        Mono<TransferenciaResponseDTO> responseMono = transferenciaApplicationService.realizarTransferencia(requestDTO);

        StepVerifier.create(responseMono)
                .expectError(ContaNotFoundException.class)
                .verify();

        verify(clienteService).buscarCliente(any());
        verify(contaService).buscarConta(any());
        verifyNoMoreInteractions(bacenService);
    }




    private Mono<TransferenciaResponseDTO> mockReturn() {
        return Mono.create(monoSink -> {
            TransferenciaResponseDTO.builder()
                    .idTransferencia("2ceb26e9-7b5c-417e-bf75-ffaa66e3a76f")
                    .build();
        });
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

    private TransferenciaRequestDTO mockRequest() {
        return TransferenciaRequestDTO.builder()
                .idCliente("2ceb26e9-7b5c-417e-bf75-ffaa66e3a76f")
                .valor(BigDecimal.TEN)
                .contaDTO(ContaOrigemDestinoDTO.builder()
                        .idDestino("41313d7b-bd75-4c75-9dea-1f4be434007f")
                        .idOrigem("d0d32142-74b7-4aca-9c68-838aeacef96b")
                        .build())
                .build();
    }

}