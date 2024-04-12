package com.client.transferencia.application.service;


import com.client.transferencia.application.exception.ApplicationException;
import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import com.client.transferencia.application.service.dto.TransferenciaResponseDTO;
import com.client.transferencia.domain.cliente.service.ClienteService;
import com.client.transferencia.domain.conta.service.ContaService;
import com.client.transferencia.domain.transferencia.service.TransferenciaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TransferenciaApplicationServiceImpl implements TransferenciaApplicationService {

    @Autowired
    private TransferenciaService transferenciaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ContaService contaService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferenciaApplicationServiceImpl.class);

    public Mono<TransferenciaResponseDTO> realizarTransferencia(TransferenciaRequestDTO transferenciaRequestDTO) {
        return clienteService.buscarCliente(transferenciaRequestDTO)
                .flatMap(requestDTO -> contaService.buscarConta(requestDTO))
                .flatMap(requestDTO -> contaService.atualizarSaldo(requestDTO))
                .flatMap(requestDTO -> transferenciaService.efetuarTransferencia(requestDTO))
                .map(responseDTO -> TransferenciaResponseDTO.builder()
                        .idTransferencia(responseDTO.getIdTransferencia())
                        .build())
                .onErrorMap(throwable -> {
                    LOGGER.error("Erro ao processar transferência: {}", throwable.getMessage());
                    return new ApplicationException("TRANSFERENCIA_ERROR", "Erro ao processar transferência");
                });
    }
}
