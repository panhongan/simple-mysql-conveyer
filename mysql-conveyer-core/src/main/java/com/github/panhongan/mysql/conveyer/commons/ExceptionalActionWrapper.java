package com.github.panhongan.mysql.conveyer.commons;

import java.util.concurrent.Callable;

/**
 * @author panhongan
 * @since 2020.9.10
 * @version 1.0
 */

public class ExceptionalActionWrapper {

    public static <T> T run(Callable<T> callable) {
        try {
            return callable.call();
        } catch (MysqlConveyerException e) {
            throw e;
        } catch (Exception e) {
            throw new MysqlConveyerException(e);
        }
    }

    public static void run(Runnable runnable) {
        Callable callable = () -> {
            runnable.run();
            return null;
        };

        run(callable);
    }
}
