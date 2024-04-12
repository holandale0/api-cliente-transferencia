package com.client.transferencia.application.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferenciaResponseDTO {

    @JsonProperty("idTransferencia")
    String idTransferencia;
}
