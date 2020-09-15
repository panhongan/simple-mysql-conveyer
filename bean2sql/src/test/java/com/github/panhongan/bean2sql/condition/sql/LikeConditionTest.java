package com.github.panhongan.bean2sql.condition.sql;

import com.github.panhongan.bean2sql.TestObj;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

public class LikeConditionTest {

    private TestObj testObj = new TestObj();

    @Before
    public void setUp() {
        testObj.setName("hello");
        testObj.setAge(100);
        testObj.setDate(new Date());
    }

    @Test
    public void testConditionSql_Ok() {
        Pair<String, Map<Integer, String>> pair = LikeCondition.builder().obj(testObj).build().conditionSql();
        System.out.println(pair);
        assert(pair.getLeft().equals("(name like ? and date like ?)"));
        assert(pair.getRight().size() == 2);
    }
}
