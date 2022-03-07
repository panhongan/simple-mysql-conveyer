package com.github.panhongan.mysql.conveyer.bean2sql.orderby;

import org.junit.Assert;
import org.junit.Test;

public class OrderByTest {

    @Test
    public void testOrderBy() {
        OrderBy<MyParam> orderBy = new OrderBy<>(MyParam.class, true);
        Assert.assertEquals(orderBy.toOrderBy(), "order by id,name");

        orderBy = new OrderBy<>(MyParam.class, false);
        Assert.assertEquals(orderBy.toOrderBy(), "order by id,name desc");
    }

    private static class MyParam {
        private Long id;
        private String name;
    }
}
