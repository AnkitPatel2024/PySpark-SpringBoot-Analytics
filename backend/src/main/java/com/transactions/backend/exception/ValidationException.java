package com.transactions.backend.exception;

public class ValidationException extends ApplicationException {
    public ValidationException(String message) {
        super(message);
    }

     public ValidationException(String message, String traceId) {
        super(message, traceId);
    }
}
