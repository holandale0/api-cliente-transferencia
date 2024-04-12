package com.client.transferencia.infrastructure.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClienteDTO {

    @JsonProperty("id")
    String id;

    @JsonProperty("nome")
    String nome;

    @JsonProperty("telefone")
    String telefone;

    @JsonProperty("tipoPessoa")
    TipoPessoaEnumDTO tipoPessoa;


}
