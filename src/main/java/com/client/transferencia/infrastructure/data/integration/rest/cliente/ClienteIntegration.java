package com.client.transferencia.infrastructure.data.integration.rest.cliente;

import com.client.transferencia.infrastructure.data.integration.configuration.FeignClientConfiguration;
import com.client.transferencia.infrastructure.shared.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente-api", url = "${integracao.url}", configuration = FeignClientConfiguration.class)
public interface ClienteIntegration {

    @GetMapping( value="/clientes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ClienteDTO obterCliente(@PathVariable("id") final String id);
}
