package com.client.transferencia.domain.transferencia.service;

import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import com.client.transferencia.application.service.dto.TransferenciaResponseDTO;
import reactor.core.publisher.Mono;


public interface TransferenciaService {
    Mono<TransferenciaResponseDTO> efetuarTransferencia(TransferenciaRequestDTO transferenciaRequestDTO);
}
