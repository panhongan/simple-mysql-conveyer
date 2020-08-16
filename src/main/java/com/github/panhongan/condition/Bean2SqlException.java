package com.github.panhongan.condition;

/**
 * @author panhongan
 * @since 2019.7.13
 * @version 1.0
 */

public class Bean2SqlException extends RuntimeException {

    public Bean2SqlException(String message) {
        super(message);
    }

    public Bean2SqlException(Throwable t) {
        super(t);
    }

    public Bean2SqlException(String message, Throwable t) {
        super(message, t);
    }
}
