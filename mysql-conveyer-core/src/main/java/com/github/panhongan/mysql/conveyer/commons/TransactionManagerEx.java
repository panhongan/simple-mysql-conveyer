package com.github.panhongan.mysql.conveyer.commons;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author panhongan
 * @since 2020.9.14
 * @version 1.0
 */

@Getter
@Service
public class TransactionManagerEx {

    @Autowired
    private TransactionTemplate transactionTemplate;

    private static Map<Thread, TransactionStatus> transactionStatusMap = new ConcurrentHashMap<>();

    public <T> T execute(Callable<T> callable) {
        return transactionTemplate.execute(transactionStatus -> {
            Thread currentThread = Thread.currentThread();

            // 事务每次执行都会创建TransactionStatus，里面包含了新的连接。
            // 为了避免连接泄漏，不允许事务里包含事务
            if (transactionStatusMap.containsKey(currentThread)) {
                throw new MysqlConveyerException("当前线程执行逻辑包含事务嵌套，请检查代码, threadName=" + currentThread.getName());
            }

            transactionStatusMap.put(currentThread, transactionStatus);

            try {
                return callable.call();
            } catch (MysqlConveyerException e) {
                throw e;
            } catch (Throwable t) {
                throw new MysqlConveyerException(t);
            } finally  {
                transactionStatusMap.remove(currentThread);
            }
        });
    }

    public void execute(Runnable runnable) {
        Callable callable = () -> {
            runnable.run();
            return null;
        };

        execute(callable);
    }

    public TransactionStatus getCurrentTransactionStatus() {
        return transactionStatusMap.get(Thread.currentThread());
    }
}
