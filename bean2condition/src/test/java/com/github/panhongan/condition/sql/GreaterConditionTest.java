package com.github.panhongan.condition.sql;

import com.github.panhongan.condition.TestObj;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class GreaterConditionTest {

    private TestObj testObj = new TestObj();

    @Before
    public void setUp() {
        testObj.setName("hello");
        testObj.setAge(100);
    }

    @Test
    public void testConditionSql_Ok() {
        GreaterCondition condition = new GreaterCondition<>();
        condition.setObj(testObj);

        Pair<String, Map<Integer, String>> pair = condition.conditionSql();
        assert (pair.getLeft().equals("(age>?)"));
        assert (pair.getRight().size() == 1);
    }
}