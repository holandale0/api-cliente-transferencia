package com.client.transferencia.application.service.dto;



import com.client.transferencia.infrastructure.shared.dto.DadosTransferenciaDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferenciaRequestDTO extends DadosTransferenciaDTO {

    @JsonProperty("idCliente")
    String idCliente;

}
