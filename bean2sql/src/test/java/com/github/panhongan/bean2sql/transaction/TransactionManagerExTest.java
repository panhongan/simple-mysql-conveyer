package com.github.panhongan.bean2sql.transaction;

import com.github.panhongan.commons.MysqlConveyerException;
import com.github.panhongan.spring.test.SpringTest;
import com.github.panhongan.spring.utils.InjectUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

public class TransactionManagerExTest extends SpringTest {
    @InjectMocks
    private TransactionManagerEx transactionManagerEx;

    @Mock
    private TransactionTemplate transactionTemplate;

    @Override
    protected void inject() {
        InjectUtils.inject(transactionManagerEx, transactionTemplate);
    }

    @Ignore
    @Test (expected = MysqlConveyerException.class)
    public void testExecute_Exception() {
        Mockito.when(transactionTemplate.execute(Mockito.any())).thenCallRealMethod();
        transactionManagerEx.execute(() -> {
            throw new RuntimeException("abc");
        });
    }
}
