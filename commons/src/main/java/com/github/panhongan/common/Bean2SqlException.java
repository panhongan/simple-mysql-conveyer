package com.github.panhongan.common;

/**
 * @author panhongan
 * @since 2019.7.13
 * @version 1.0
 */

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
