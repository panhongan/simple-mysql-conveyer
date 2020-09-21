package com.github.panhongan.bean2sql.condition.impl;

import com.github.panhongan.bean2sql.condition.SqlConditionMaker;
import com.github.panhongan.bean2sql.TestObj;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * @author panhongan
 * @since 2020.9.15
 * @version 1.0
 */

public class AndConditionTest {

    private TestObj testObj1 = new TestObj();

    private TestObj testObj2 = new TestObj();

    @Before
    public void setUp() {
        testObj1.setAge(100);
        testObj2.setName("hello");
    }

    @Test
    public void testConditionSql_Ok() {
        LessCondition lessCondition = SqlConditionMaker.lessCondition(testObj1);
        EqualCondition equalCondition = SqlConditionMaker.equalCondition(testObj2);

        AndCondition andCondition = SqlConditionMaker.andCondition();
        andCondition.add(lessCondition).add(equalCondition);

        Pair<String, Map<Integer, String>> pair = andCondition.conditionSql();
        assert(pair.getLeft().equals("((age<?) and (name=?))"));
        assert(pair.getRight().size() == 2);
    }
}
