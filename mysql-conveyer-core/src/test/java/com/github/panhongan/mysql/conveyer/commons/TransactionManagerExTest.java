package com.github.panhongan.mysql.conveyer.commons;

import com.github.panhongan.spring.test.SpringTest;
import com.github.panhongan.spring.utils.InjectUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author lalaluplus
 * @since 2020.7.1
 */
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
        transactionManagerEx.execute(() -> {
            throw new RuntimeException("abc");
        });
    }
}
