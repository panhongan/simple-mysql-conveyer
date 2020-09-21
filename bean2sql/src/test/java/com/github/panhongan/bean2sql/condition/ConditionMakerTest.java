package com.github.panhongan.bean2sql.condition;

import com.github.panhongan.bean2sql.TestObj;
import com.github.panhongan.bean2sql.condition.sql.AndCondition;
import com.github.panhongan.bean2sql.condition.sql.BetweenAndCondition;
import com.github.panhongan.bean2sql.condition.sql.EqualCondition;
import com.github.panhongan.bean2sql.condition.sql.LessCondition;
import com.github.panhongan.bean2sql.condition.sql.OrCondition;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

/**
 * @author panhongan
 * @since 2020.9.15
 * @version 1.0
 */

public class ConditionMakerTest {

    @Test
    public void testEqualCondition_Ok() {
        TestObj testObj = new TestObj();
        testObj.setName("hello");
        testObj.setAge(100);

        Pair<String, Map<Integer, String>> pair = ConditionMaker.equalCondition(testObj).conditionSql();
        assert(pair.getLeft().equals("(name=? and age=?)"));
        assert(pair.getRight().size() == 2);
    }

    @Test
    public void testNotEqualCondition_Ok() {
        TestObj testObj = new TestObj();
        testObj.setName("hello");
        testObj.setAge(100);

        Pair<String, Map<Integer, String>> pair = ConditionMaker.notEqualCondition(testObj).conditionSql();
        assert(pair.getLeft().equals("(name!=? and age!=?)"));
        assert(pair.getRight().size() == 2);
    }

    @Test
    public void testGreaterCondition_Ok() {
        TestObj testObj = new TestObj();
        testObj.setName("hello");
        testObj.setAge(100);

        Pair<String, Map<Integer, String>> pair = ConditionMaker.greaterCondition(testObj).conditionSql();
        assert(pair.getLeft().equals("(age>?)"));
        assert(pair.getRight().size() == 1);
    }

    @Test
    public void testGreaterOrCondition_Ok() {
        TestObj testObj = new TestObj();
        testObj.setName("hello");
        testObj.setAge(100);

        Pair<String, Map<Integer, String>> pair = ConditionMaker.greaterOrEqualCondition(testObj).conditionSql();
        assert(pair.getLeft().equals("(age>=?)"));
        assert(pair.getRight().size() == 1);
    }

    @Test
    public void testLessCondition_Ok() {
        TestObj testObj = new TestObj();
        testObj.setName("hello");
        testObj.setAge(100);

        Pair<String, Map<Integer, String>> pair = ConditionMaker.lessCondition(testObj).conditionSql();
        assert(pair.getLeft().equals("(age<?)"));
        assert(pair.getRight().size() == 1);
    }

    @Test
    public void testLessOrEqualCondition_Ok() {
        TestObj testObj = new TestObj();
        testObj.setName("hello");
        testObj.setAge(100);

        Pair<String, Map<Integer, String>> pair = ConditionMaker.lessOrEqualCondition(testObj).conditionSql();
        assert(pair.getLeft().equals("(age<=?)"));
        assert(pair.getRight().size() == 1);
    }

    @Test
    public void testLikeCondition_Ok() {
        TestObj testObj = new TestObj();
        testObj.setName("hello");
        testObj.setAge(100);
        testObj.setDate(new Date());

        Pair<String, Map<Integer, String>> pair = ConditionMaker.likeCondition(testObj).conditionSql();
        assert(pair.getLeft().equals("(name like ? and date like ?)"));
        assert(pair.getRight().size() == 2);
    }

    @Test
    public void testAndCondition_Ok() {
        TestObj testObj1 = new TestObj();
        testObj1.setAge(100);

        TestObj testObj2 = new TestObj();
        testObj2.setName("hello");

        LessCondition lessCondition = ConditionMaker.lessCondition(testObj1);
        EqualCondition equalCondition = ConditionMaker.equalCondition(testObj2);

        AndCondition andCondition = ConditionMaker.andCondition();
        andCondition.add(lessCondition).add(equalCondition);

        Pair<String, Map<Integer, String>> pair = andCondition.conditionSql();
        assert(pair.getLeft().equals("((age<?) and (name=?))"));
        assert(pair.getRight().size() == 2);
    }

    @Test
    public void testOrCondition_Ok() {
        TestObj testObj1 = new TestObj();
        testObj1.setAge(100);

        TestObj testObj2 = new TestObj();
        testObj2.setName("hello");

        LessCondition lessCondition = ConditionMaker.lessCondition(testObj1);
        EqualCondition equalCondition = ConditionMaker.equalCondition(testObj2);

        OrCondition andCondition = ConditionMaker.orCondition();
        andCondition.add(lessCondition).add(equalCondition);

        Pair<String, Map<Integer, String>> pair = andCondition.conditionSql();
        assert(pair.getLeft().equals("((age<?) or (name=?))"));
        assert(pair.getRight().size() == 2);
    }

    @Test
    public void testBetweenAndCondition_Ok() {
        BetweenAndCondition betweenAndCondition = ConditionMaker.betweenAndCondition("create_date", new Date(), new Date());
        Pair<String, Map<Integer, String>> pair = betweenAndCondition.conditionSql();
        assert(pair.getLeft().equals("(create_date between ? and ?)"));
        assert(pair.getRight().size() == 2);
    }
}
