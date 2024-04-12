package com.client.transferencia.domain.bacen.service;

import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import com.client.transferencia.application.service.dto.TransferenciaResponseDTO;
import com.client.transferencia.infrastructure.data.exception.InfrastructureException;
import com.client.transferencia.infrastructure.data.integration.rest.bacen.BacenIntegration;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class BacenServiceImpl implements BacenService{


    @Autowired
    private BacenIntegration bacenIntegration;

    private static final Logger LOGGER = LoggerFactory.getLogger(BacenServiceImpl.class);

    @Override
    public Mono<TransferenciaResponseDTO> notificarBacen(TransferenciaRequestDTO requestDTO, TransferenciaResponseDTO responseDTO) {
        return Mono.create(monoSink -> {
            try{
            bacenIntegration.notificarBacen(requestDTO);
            monoSink.success(responseDTO);
            }catch (Exception e){
                monoSink.error(new InfrastructureException("Erro no serviço externo do bacen."));
            }
        });
    }
}
