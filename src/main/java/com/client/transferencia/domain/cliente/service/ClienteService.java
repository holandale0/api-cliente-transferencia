package com.client.transferencia.domain.cliente.service;

import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import reactor.core.publisher.Mono;

public interface ClienteService {
    Mono<TransferenciaRequestDTO> buscarCliente(TransferenciaRequestDTO request);
}
