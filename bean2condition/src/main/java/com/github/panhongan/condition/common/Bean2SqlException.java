package com.github.panhongan.condition.common;

public class Bean2SqlException extends RuntimeException {

    public Bean2SqlException(String message) {
        super(message);
    }

    public Bean2SqlException(Exception e) {
        super(e);
    }

    public Bean2SqlException(String message, Exception e) {
        super(message, e);
    }
}
