package com.client.transferencia.infrastructure.shared.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoPessoaEnumDTO {

    PESSOA_JURIDICA("Juridica"), PESSOA_FISICA("Fisica");

    private String descricao;

    TipoPessoaEnumDTO(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }
}
