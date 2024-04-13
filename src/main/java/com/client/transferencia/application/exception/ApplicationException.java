package com.client.transferencia.application.exception;

public class ApplicationException extends RuntimeException {
    private final String errorCode;
    private final String message;

    public ApplicationException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
