package com.transactions.backend.exception;

public abstract class ApplicationException extends RuntimeException {

    private final String traceId;

    public ApplicationException(String message) {
        super(message);
        this.traceId = null; // You can set this to a unique identifier if needed  

    }   
    
      public ApplicationException(String message, String traceId) {
        super(message);
        this.traceId = traceId;
    }

    public String getTraceId() {
        return traceId;
    }

}
