package com.ace.common.exception;

/**
 * Created by bamboo on 17-12-1.
 */
// for HTTP 400 errors
public final class DataFormatException extends RuntimeException {
    public DataFormatException() {
        super();
    }

    public DataFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataFormatException(String message) {
        super(message);
    }

    public DataFormatException(Throwable cause) {
        super(cause);
    }
}
