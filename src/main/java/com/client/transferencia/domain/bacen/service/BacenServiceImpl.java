package com.client.transferencia.domain.bacen.service;

import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import com.client.transferencia.application.service.dto.TransferenciaResponseDTO;
import com.client.transferencia.infrastructure.data.exception.InfrastructureException;
import com.client.transferencia.infrastructure.data.integration.rest.bacen.BacenIntegration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BacenServiceImpl implements BacenService {


    @Autowired
    private BacenIntegration bacenIntegration;

    private static final Logger LOGGER = LoggerFactory.getLogger(BacenServiceImpl.class);

    @Override
    public Mono<TransferenciaResponseDTO> notificarBacen(TransferenciaRequestDTO requestDTO) {
        return Mono.create(monoSink -> {
            try {
                TransferenciaResponseDTO responseDTO = TransferenciaResponseDTO.builder()
                        .idTransferencia("410bb5b0-429f-46b1-8621-b7da101b1e28").build();
                bacenIntegration.notificarBacen(requestDTO);
                monoSink.success(responseDTO);
            } catch (Exception e) {
                monoSink.error(new InfrastructureException("Erro no serviço externo do bacen."));
            }
        });
    }
}
