package com.transactions.backend.exception;

public  class DataNotFoundException extends ApplicationException {
    public DataNotFoundException(String message) {
        super(message);
    }
    
     public DataNotFoundException(String message, String traceId) {
        super(message, traceId);
    }
}
