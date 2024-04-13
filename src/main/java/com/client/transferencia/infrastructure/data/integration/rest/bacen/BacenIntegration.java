package com.client.transferencia.infrastructure.data.integration.rest.bacen;


import com.client.transferencia.infrastructure.data.integration.configuration.FeignClientConfiguration;
import com.client.transferencia.infrastructure.shared.dto.DadosTransferenciaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;


@FeignClient(name = "bacen-api", url = "${integracao.url}", configuration = FeignClientConfiguration.class)
public interface BacenIntegration {

    @PostMapping(value = "/notificacoes", consumes = MediaType.APPLICATION_JSON_VALUE)
    Mono<Void> notificarBacen(@RequestBody DadosTransferenciaDTO request);

}
