package com.client.transferencia.infrastructure.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContaDTO {

    @JsonProperty("id")
    String id;

    @JsonProperty("saldo")
    BigDecimal saldo;

    @JsonProperty("ativo")
    Boolean ativo;

    @JsonProperty("limiteDiario")
    BigDecimal limiteDiario;

}
