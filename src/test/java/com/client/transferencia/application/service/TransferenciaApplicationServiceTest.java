package com.client.transferencia.application.service;

import com.client.transferencia.application.exception.ApplicationException;
import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import com.client.transferencia.application.service.dto.TransferenciaResponseDTO;
import com.client.transferencia.domain.cliente.service.ClienteService;
import com.client.transferencia.domain.conta.service.ContaService;
import com.client.transferencia.domain.transferencia.service.TransferenciaService;
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
    private TransferenciaService transferenciaService;

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
        when(transferenciaService.efetuarTransferencia(any())).thenReturn(Mono.just(responseDTO));

        Mono<TransferenciaResponseDTO> responseMono = transferenciaApplicationService.realizarTransferencia(requestDTO);

        StepVerifier.create(responseMono)
                .expectNext(responseDTO)
                .verifyComplete();

        verify(clienteService).buscarCliente(any());
        verify(contaService).buscarConta(any());
        verify(contaService).atualizarSaldo(any());
        verify(transferenciaService).efetuarTransferencia(any());
    }

    @Test
    public void testeEfetuarTransferenciaFalhaAoBuscarCliente() {
        TransferenciaRequestDTO requestDTO = mockRequest();

        when(clienteService.buscarCliente(any())).thenReturn(Mono.error(new ApplicationException("Erro ao buscar cliente","")));

        Mono<TransferenciaResponseDTO> responseMono = transferenciaApplicationService.realizarTransferencia(requestDTO);

        StepVerifier.create(responseMono)
                .expectError(ApplicationException.class)
                .verify();

        verify(clienteService).buscarCliente(any());
        verifyNoInteractions(contaService, transferenciaService);
    }

    @Test
    public void testeEfetuarTransferenciaFalhaAoBuscarConta() {
        TransferenciaRequestDTO requestDTO = mockRequest();

        when(clienteService.buscarCliente(any())).thenReturn(Mono.just(requestDTO));
        when(contaService.buscarConta(any())).thenReturn(Mono.error(new ApplicationException("Erro ao buscar conta","")));

        Mono<TransferenciaResponseDTO> responseMono = transferenciaApplicationService.realizarTransferencia(requestDTO);

        StepVerifier.create(responseMono)
                .expectError(ApplicationException.class)
                .verify();

        verify(clienteService).buscarCliente(any());
        verify(contaService).buscarConta(any());
        verifyNoMoreInteractions(transferenciaService);
    }

    //@Test
    public void testeEfetuarTransferenciaFalhaAoEfetuarTransferencia() {
        TransferenciaRequestDTO requestDTO = mockRequest();

        when(clienteService.buscarCliente(any())).thenReturn(Mono.just(requestDTO));
        when(contaService.buscarConta(any())).thenReturn(Mono.just(requestDTO));
        when(transferenciaService.efetuarTransferencia(any(TransferenciaRequestDTO.class))).thenReturn(Mono.error(new ApplicationException("Erro ao efetuar transferência","")));

        Mono<TransferenciaResponseDTO> responseMono = transferenciaApplicationService.realizarTransferencia(requestDTO);

        StepVerifier.create(responseMono)
                .expectError(ApplicationException.class)
                .verify();

        verify(clienteService).buscarCliente(any());
        verify(contaService).buscarConta(any());
        verify(transferenciaService).efetuarTransferencia(any(TransferenciaRequestDTO.class));
    }


    private Mono<TransferenciaResponseDTO> mockReturn(){
        return Mono.create(monoSink -> {
            TransferenciaResponseDTO.builder()
                .idTransferencia("2ceb26e9-7b5c-417e-bf75-ffaa66e3a76f")
                .build();});
    }

    private Mono<TransferenciaRequestDTO> mockObject(){
        return Mono.create(monoSink -> {TransferenciaRequestDTO.builder()
                .idCliente("2ceb26e9-7b5c-417e-bf75-ffaa66e3a76f")
                .valor(BigDecimal.TEN)
                .contaDTO(ContaOrigemDestinoDTO.builder()
                        .idDestino("41313d7b-bd75-4c75-9dea-1f4be434007f")
                        .idOrigem("d0d32142-74b7-4aca-9c68-838aeacef96b")
                        .build())
                .build();});
    }

    private TransferenciaRequestDTO mockRequest(){
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