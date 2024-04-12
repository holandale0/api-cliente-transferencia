package com.client.transferencia.infrastructure.data.integration.rest.conta;


import com.client.transferencia.infrastructure.data.integration.configuration.FeignClientConfiguration;
import com.client.transferencia.infrastructure.shared.dto.ContaDTO;
import com.client.transferencia.infrastructure.shared.dto.DadosTransferenciaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@FeignClient(name = "conta-api", url = "${integracao.url}", configuration = FeignClientConfiguration.class)
public interface ContaIntegration {

    @GetMapping( value="/contas/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ContaDTO obterConta(@PathVariable("id") final String id);

    @PutMapping(value = "/contas/saldos", consumes = MediaType.APPLICATION_JSON_VALUE)
    Mono<Void> atualizarSaldo(@RequestBody DadosTransferenciaDTO request);
}
