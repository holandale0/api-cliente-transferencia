package com.client.transferencia.infrastructure.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContaOrigemDestinoDTO {

    @JsonProperty("idOrigem")
    String idOrigem;

    @JsonProperty("idDestino")
    String idDestino;
}
