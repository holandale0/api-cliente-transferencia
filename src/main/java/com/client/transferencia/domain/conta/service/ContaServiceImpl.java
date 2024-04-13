package com.client.transferencia.domain.conta.service;


import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import com.client.transferencia.domain.exception.*;
import com.client.transferencia.infrastructure.data.exception.InfrastructureException;
import com.client.transferencia.infrastructure.data.integration.rest.conta.ContaIntegration;
import com.client.transferencia.infrastructure.shared.dto.ContaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;


@Service
public class ContaServiceImpl implements ContaService {

    @Autowired
    private ContaIntegration contaIntegration;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContaServiceImpl.class);

    public Mono<TransferenciaRequestDTO> buscarConta(TransferenciaRequestDTO request) {
        return Mono.create(monoSink -> {
            try {
                ContaDTO conta = contaIntegration.obterConta(request.getContaDTO().getIdOrigem());
                if (conta == null) {
                    monoSink.error(new ContaNotFoundException("Conta não encontrada para o ID: " + request.getContaDTO().getIdOrigem()));
                }
                if (!conta.getAtivo()) {
                    monoSink.error(new ContaInactiveException("A conta está inativa: " + request.getContaDTO().getIdOrigem()));
                }
                if (conta.getSaldo().compareTo(BigDecimal.ZERO) <= 0) {
                    monoSink.error(new InsufficientBalanceException(String.format("A conta não possui saldo suficiente. Saldo disponível: %s, Valor solicitado: %s", conta.getSaldo(), request.getValor())));
                }
                if (conta.getLimiteDiario().compareTo(BigDecimal.ZERO) == 0 || request.getValor().compareTo(conta.getLimiteDiario()) > 0) {
                    monoSink.error(new ExceededDailyLimitException(String.format("O valor solicitado na transferência está acima do limite diário. Limite diário: %s, Valor solicitado: %s", conta.getSaldo(), request.getValor())));
                }
                monoSink.success(request);
            } catch (Exception e) {
                monoSink.error(new InfrastructureException("Erro no serviço externo de conta."));
            }

        });
    }


    public Mono<TransferenciaRequestDTO> atualizarSaldo(TransferenciaRequestDTO request) {
        return Mono.create(monoSink -> {
            try {
                contaIntegration.atualizarSaldo(request);
                monoSink.success(request);
            } catch (Exception e) {
                monoSink.error(new InfrastructureException("Erro no serviço externo de conta."));
            }
        });

    }

}
