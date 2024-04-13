package com.client.transferencia.domain.exception;

public class ExceededDailyLimitException extends RuntimeException {

    public ExceededDailyLimitException(String message) {
        super(message);
    }
}
