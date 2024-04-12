package com.client.transferencia.domain.conta.service;

import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import com.client.transferencia.domain.exception.DomainException;
import com.client.transferencia.infrastructure.data.integration.rest.conta.ContaIntegration;
import com.client.transferencia.infrastructure.shared.dto.ContaDTO;
import com.client.transferencia.infrastructure.shared.dto.ContaOrigemDestinoDTO;
import com.client.transferencia.infrastructure.shared.dto.DadosTransferenciaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class ContaServiceTest {

    @InjectMocks
    private ContaService contaService = new ContaServiceImpl();

    @Mock
    private ContaIntegration contaIntegration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarConta_Sucesso() {

        ContaDTO contaOk = contaMock();

        when(contaIntegration.obterConta(anyString())).thenReturn(contaOk);

        Mono<TransferenciaRequestDTO> resultMono = contaService.buscarConta(mockRequest());

        StepVerifier.create(resultMono)
                .expectNext(mockRequest())
                .verifyComplete();

        verify(contaIntegration).obterConta(anyString());
    }

    @Test
    void atualizarSaldo_Sucesso() {

        when(contaIntegration.atualizarSaldo(any(DadosTransferenciaDTO.class))).thenReturn(Mockito.any());

        Mono<TransferenciaRequestDTO> resultMono = contaService.atualizarSaldo(mockRequest());

        StepVerifier.create(resultMono)
                .expectNext(mockRequest())
                .verifyComplete();

        verify(contaIntegration).atualizarSaldo(mockRequest());
    }

    @Test
    void buscarConta_ContaNula() {
        when(contaIntegration.obterConta(anyString())).thenReturn(null);

        Mono<TransferenciaRequestDTO> resultMono = contaService.buscarConta(mockRequest());

        StepVerifier.create(resultMono)
                .expectError(DomainException.class)
                .verify();
    }

    @Test
    void buscarConta_ContaInativa() {
        ContaDTO contaInativa = contaMock();
        contaInativa.setAtivo(false);
        when(contaIntegration.obterConta(anyString())).thenReturn(contaInativa);

        Mono<TransferenciaRequestDTO> resultMono = contaService.buscarConta(mockRequest());

        StepVerifier.create(resultMono)
                .expectError(DomainException.class)
                .verify();
    }

    @Test
    void buscarConta_SaldoInsuficiente() {
        ContaDTO contaSaldoInsuficiente = contaMock();
        contaSaldoInsuficiente.setSaldo(BigDecimal.ZERO);
        when(contaIntegration.obterConta(anyString())).thenReturn(contaSaldoInsuficiente);

        Mono<TransferenciaRequestDTO> resultMono = contaService.buscarConta(mockRequest());

        StepVerifier.create(resultMono)
                .expectError(DomainException.class)
                .verify();
    }

    @Test
    void buscarConta_LimiteDiarioExcedido() {
        ContaDTO contaLimiteDiarioExcedido = contaMock();
        contaLimiteDiarioExcedido.setLimiteDiario(BigDecimal.ONE);

        mockRequest().setValor(BigDecimal.TEN);
        when(contaIntegration.obterConta(anyString())).thenReturn(contaLimiteDiarioExcedido);

        Mono<TransferenciaRequestDTO> resultMono = contaService.buscarConta(mockRequest());

        StepVerifier.create(resultMono)
                .expectError(DomainException.class)
                .verify();
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

    private ContaDTO contaMock() {
        return ContaDTO.builder()
                .id("contaId")
                .saldo(BigDecimal.TEN)
                .ativo(true)
                .limiteDiario(BigDecimal.valueOf(1000))
                .build();
    }
}