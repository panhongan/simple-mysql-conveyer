package com.github.panhongan.condition.sql;

import com.github.panhongan.condition.TestObj;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

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
        LessCondition lessCondition = new LessCondition();
        lessCondition.setObj(testObj1);

        EqualCondition equalCondition = EqualCondition.builder().obj(testObj2).build();

        AndCondition andCondition = new AndCondition();
        andCondition.add(lessCondition).add(equalCondition);

        Pair<String, Map<Integer, String>> pair = andCondition.conditionSql();
        assert (pair.getLeft().equals("((age<?) and (name=?))"));
        assert (pair.getRight().size() == 2);
    }
}
