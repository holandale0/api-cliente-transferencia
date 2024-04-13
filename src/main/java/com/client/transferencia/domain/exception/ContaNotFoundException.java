package com.client.transferencia.domain.exception;

public class ContaNotFoundException extends RuntimeException {

    public ContaNotFoundException(String message) {
        super(message);
    }
}
