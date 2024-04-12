package com.client.transferencia.domain.exception;

public class DomainException extends RuntimeException {
    private String entityName;
    private String message;

    public DomainException(String entityName, String message) {
        this.entityName = entityName;
        this.message = message;

    }
}
