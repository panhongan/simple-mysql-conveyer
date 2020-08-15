package com.github.panhongan.dal.core;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Service
public class TransactionManagerEx {

    @Autowired
    private TransactionTemplate transactionTemplate;

    private static Map<Thread, TransactionStatus> transactionStatusMap = new ConcurrentHashMap<>();

    public <T> T execute(Callable<T> callable) {
        return transactionTemplate.execute(transactionStatus -> {
            transactionStatusMap.put(Thread.currentThread(), transactionStatus);

            try {
                return callable.call();
            } catch (Throwable t) {
                throw new RuntimeException(t);
            } finally  {
                transactionStatusMap.remove(Thread.currentThread());
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
