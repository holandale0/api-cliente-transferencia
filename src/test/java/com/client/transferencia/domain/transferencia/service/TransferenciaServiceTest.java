package com.client.transferencia.domain.transferencia.service;

import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import com.client.transferencia.application.service.dto.TransferenciaResponseDTO;
import com.client.transferencia.infrastructure.data.exception.InfrastructureException;
import com.client.transferencia.infrastructure.data.integration.rest.bacen.BacenIntegration;
import com.client.transferencia.infrastructure.data.integration.rest.transferencia.TransferenciaIntegration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



public class TransferenciaServiceTest {

    @InjectMocks
    private TransferenciaService transferenciaService = new TransferenciaServiceImpl();

    @Mock
    private TransferenciaIntegration transferenciaIntegration;

    @Mock
    private BacenIntegration bacenIntegration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void efetuarTransferencia_Sucesso() {
        TransferenciaRequestDTO requestDTO = new TransferenciaRequestDTO();
        TransferenciaResponseDTO responseDTO = TransferenciaResponseDTO.builder().idTransferencia("410bb5b0-429f-46b1-8621-b7da101b1e28").build();
        when(transferenciaIntegration.realizarTransferencia(any())).thenReturn(responseDTO);

        Mono<TransferenciaResponseDTO> resultMono = transferenciaService.efetuarTransferencia(requestDTO);

        StepVerifier.create(resultMono)
                .expectNext(responseDTO)
                .verifyComplete();

        verify(bacenIntegration).notificarBacen(requestDTO);
    }

    @Test
    void efetuarTransferencia_Erro() {
        TransferenciaRequestDTO requestDTO = new TransferenciaRequestDTO();
        when(transferenciaIntegration.realizarTransferencia(any())).thenThrow(new RuntimeException("Erro ao efetuar a transferência"));

        Mono<TransferenciaResponseDTO> resultMono = transferenciaService.efetuarTransferencia(requestDTO);

        StepVerifier.create(resultMono)
                .expectError(InfrastructureException.class)
                .verify();

        // Verifica se a integração com o Bacen não é chamada em caso de erro
        verify(bacenIntegration).notificarBacen(any());
    }


}