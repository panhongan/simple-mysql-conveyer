package com.github.panhongan.bean2sql.condition.impl;

import com.github.panhongan.bean2sql.TestObj;
import com.github.panhongan.bean2sql.condition.SqlConditionMaker;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * @author panhongan
 * @since 2020.9.15
 * @version 1.0
 */

public class GreaterOrEqualConditionTest {

    private TestObj testObj = new TestObj();

    @Before
    public void setUp() {
        testObj.setName("hello");
        testObj.setAge(100);
    }

    @Test
    public void testConditionSql_Ok() {
        Pair<String, Map<Integer, String>> pair = SqlConditionMaker.greaterOrEqualCondition(testObj).conditionSql();
        assert(pair.getLeft().equals("(age>=?)"));
        assert(pair.getRight().size() == 1);
    }
}
