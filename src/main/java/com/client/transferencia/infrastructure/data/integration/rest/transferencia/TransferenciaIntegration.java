package com.client.transferencia.infrastructure.data.integration.rest.transferencia;


import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import com.client.transferencia.application.service.dto.TransferenciaResponseDTO;
import com.client.transferencia.infrastructure.data.integration.configuration.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "transferencia-api", url = "${transferencia.url}", configuration = FeignClientConfiguration.class)
public interface TransferenciaIntegration {

    @PostMapping(value = "/transferencia", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    TransferenciaResponseDTO realizarTransferencia(@RequestBody TransferenciaRequestDTO request);

}
