package com.client.transferencia.domain.bacen.service;

import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import com.client.transferencia.application.service.dto.TransferenciaResponseDTO;
import com.client.transferencia.infrastructure.data.exception.InfrastructureException;
import com.client.transferencia.infrastructure.data.integration.rest.bacen.BacenIntegration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;




public class BacenServiceTest {


    @InjectMocks
    BacenService bacenService = new BacenServiceImpl();

    @Mock
    private BacenIntegration bacenIntegration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void notificarBacen_Sucesso() {

        TransferenciaRequestDTO requestDTO = new TransferenciaRequestDTO();
        TransferenciaResponseDTO responseDTO = TransferenciaResponseDTO.builder().idTransferencia("410bb5b0-429f-46b1-8621-b7da101b1e28").build();

        when(bacenIntegration.notificarBacen(any())).thenReturn(Mockito.any());

        Mono<TransferenciaResponseDTO> resultMono = bacenService.notificarBacen(requestDTO);

        StepVerifier.create(resultMono)
                .expectNext(responseDTO)
                .verifyComplete();

        verify(bacenIntegration).notificarBacen(requestDTO);
    }

    @Test
    void notificarBacen_Erro() {

        TransferenciaRequestDTO requestDTO = new TransferenciaRequestDTO();
        when(bacenIntegration.notificarBacen(any())).thenThrow(new InfrastructureException("Erro ao efetuar a transferência"));

        Mono<TransferenciaResponseDTO> resultMono = bacenService.notificarBacen(requestDTO);

        StepVerifier.create(resultMono)
                .expectError(InfrastructureException.class)
                .verify();

        verify(bacenIntegration).notificarBacen(any());
    }


}