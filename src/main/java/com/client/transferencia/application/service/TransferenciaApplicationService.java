package com.client.transferencia.application.service;

import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import com.client.transferencia.application.service.dto.TransferenciaResponseDTO;
import reactor.core.publisher.Mono;

public interface TransferenciaApplicationService {

    Mono<TransferenciaResponseDTO> realizarTransferencia(TransferenciaRequestDTO transferenciaRequestDTO);

}
