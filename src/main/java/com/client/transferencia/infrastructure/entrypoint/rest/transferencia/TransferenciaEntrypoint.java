package com.client.transferencia.infrastructure.entrypoint.rest.transferencia;

import com.client.transferencia.application.service.TransferenciaApplicationService;

import com.client.transferencia.application.service.dto.TransferenciaRequestDTO;
import com.client.transferencia.application.service.dto.TransferenciaResponseDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("transferencia")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TransferenciaEntrypoint {

    @Autowired
    TransferenciaApplicationService transferenciaApplicationService;

    @Operation(summary = "Realizar transferência")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferência realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TransferenciaRequestDTO.class))}
            ),
            @ApiResponse(responseCode = "422", description = "Não foi possível realizar a transferência, verifique as informações"),
    })
    @PostMapping("/efetuar")
    public Mono<TransferenciaResponseDTO> close(@RequestBody TransferenciaRequestDTO transferenciaRequestDTO) {
        return transferenciaApplicationService.realizarTransferencia(transferenciaRequestDTO);
    }
}
