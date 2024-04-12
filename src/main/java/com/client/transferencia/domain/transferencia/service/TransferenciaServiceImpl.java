package com.client.transferencia.domain.transferencia.service;



import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import com.client.transferencia.application.service.dto.TransferenciaResponseDTO;
import com.client.transferencia.infrastructure.data.exception.InfrastructureException;
import com.client.transferencia.infrastructure.data.integration.rest.bacen.BacenIntegration;
import com.client.transferencia.infrastructure.data.integration.rest.transferencia.TransferenciaIntegration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class TransferenciaServiceImpl implements TransferenciaService {

    @Autowired
    private TransferenciaIntegration transferenciaIntegration;

    @Autowired
    private BacenIntegration bacenIntegration;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferenciaServiceImpl.class);

    public Mono<TransferenciaResponseDTO> efetuarTransferencia(TransferenciaRequestDTO transferenciaRequestDTO) {
        return Mono.create(monoSink -> {
            TransferenciaResponseDTO response = TransferenciaResponseDTO.builder().idTransferencia("410bb5b0-429f-46b1-8621-b7da101b1e28").build();
            try{
                response = transferenciaIntegration.realizarTransferencia(transferenciaRequestDTO);
            }catch(Exception e){
                monoSink.error(new InfrastructureException("Erro no serviço externo de transferência"));
            }
            try{
                bacenIntegration.notificarBacen(transferenciaRequestDTO);
                monoSink.success(response);
            }catch (Exception e){
                monoSink.error(new InfrastructureException("Erro no serviço externo do bacen."));
            }
        });
    }
}
