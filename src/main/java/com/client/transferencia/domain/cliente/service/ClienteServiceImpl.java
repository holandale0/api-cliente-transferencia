package com.client.transferencia.domain.cliente.service;


import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import com.client.transferencia.infrastructure.data.exception.InfrastructureException;
import com.client.transferencia.infrastructure.data.integration.rest.cliente.ClienteIntegration;
import com.client.transferencia.infrastructure.shared.dto.ClienteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteIntegration clienteIntegration;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteServiceImpl.class);

    public Mono<TransferenciaRequestDTO> buscarCliente(TransferenciaRequestDTO request) {
        return Mono.create(monoSink -> {
            try {
                ClienteDTO cliente = clienteIntegration.obterCliente(request.getIdCliente());
                if (cliente != null) {
                    System.out.println("Cliente: " + cliente);
                    monoSink.success(request);
                } else {
                    monoSink.error(new InfrastructureException("Cliente não encontrado para o ID: " + request.getIdCliente()));
                }
            } catch (Exception e) {
                monoSink.error(new InfrastructureException("Erro no serviço externo de cliente."));
            }
        });

    }
}
