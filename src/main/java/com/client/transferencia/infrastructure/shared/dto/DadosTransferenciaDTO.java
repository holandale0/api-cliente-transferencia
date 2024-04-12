package com.client.transferencia.infrastructure.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DadosTransferenciaDTO {

    @JsonProperty("valor")
    BigDecimal valor;

    @JsonProperty("conta")
    ContaOrigemDestinoDTO contaDTO;

}
