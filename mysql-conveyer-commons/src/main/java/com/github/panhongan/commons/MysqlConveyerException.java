package com.github.panhongan.commons;

/**
 * @author panhongan
 * @since 2019.7.13
 * @version 1.0
 */

public class MysqlConveyerException extends RuntimeException {

    public MysqlConveyerException(String message) {
        super(message);
    }

    public MysqlConveyerException(Throwable t) {
        super(t);
    }

    public MysqlConveyerException(String message, Throwable t) {
        super(message, t);
    }
}
