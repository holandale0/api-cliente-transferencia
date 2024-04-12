package com.client.transferencia.domain.bacen.service;

import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import com.client.transferencia.application.service.dto.TransferenciaResponseDTO;
import reactor.core.publisher.Mono;

public interface BacenService {

    Mono<TransferenciaResponseDTO> notificarBacen(TransferenciaRequestDTO requestDTO, TransferenciaResponseDTO responseDTO);
}
