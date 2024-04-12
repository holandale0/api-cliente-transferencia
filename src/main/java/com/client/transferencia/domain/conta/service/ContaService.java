package com.client.transferencia.domain.conta.service;

import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import reactor.core.publisher.Mono;

public interface ContaService {

    Mono<TransferenciaRequestDTO> buscarConta(TransferenciaRequestDTO request);

    Mono<TransferenciaRequestDTO> atualizarSaldo(TransferenciaRequestDTO request);
}
