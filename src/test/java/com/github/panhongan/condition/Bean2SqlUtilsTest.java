package com.github.panhongan.condition;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.Map;

public class Bean2SqlUtilsTest {

    @Test
    public void testGetEqualTypeConditionSql_ConditionObjectIsNull() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getEqualTypeConditionSql(null, ConditionOperator.EQUAL);
        assert (pair.getLeft().isEmpty());
    }

    @Test
    public void testGetEqualTypeConditionSql_ConditionOperatorIsNotEqualType() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getEqualTypeConditionSql(new TestObj(), null);
        assert (pair.getLeft().isEmpty());
    }

    @Test
    public void testGetEqualTypeConditionSql_ConditionObjectIsNotSet() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getEqualTypeConditionSql(new TestObj(), ConditionOperator.EQUAL);
        assert (pair.getLeft().isEmpty());
    }

    @Test
    public void testGetEqualTypeConditionSql_ConditionObjectIsOk() {
        TestObj obj = new TestObj();
        obj.setAge(1);
        obj.setName("hello");

        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getEqualTypeConditionSql(obj, ConditionOperator.EQUAL);
        assert (!pair.getLeft().isEmpty());
        assert (pair.getLeft().equals("(name=? and age=?)"));
        assert (pair.getRight().size() == 2);
    }

    @Test
    public void testGetComparableConditionSql_ConditionObjectIsNull() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getComparableConditionSql(null, ConditionOperator.EQUAL);
        assert (pair.getLeft().isEmpty());
    }

    @Test
    public void testGetComparableConditionSql_ConditionOperatorIsNotComparableType() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getComparableConditionSql(new TestObj(), null);
        assert (pair.getLeft().isEmpty());
    }

    @Test
    public void testGetComparableConditionSql_ConditionObjectIsNotSet() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getComparableConditionSql(new TestObj(), ConditionOperator.LESS);
        assert (pair.getLeft().isEmpty());
    }

    @Test
    public void testGetComparableConditionSql_ConditionObjectIsOk() {
        TestObj obj = new TestObj();
        obj.setAge(1);
        obj.setName("hello");

        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getComparableConditionSql(obj, ConditionOperator.LESS);
        assert (!pair.getLeft().isEmpty());
        assert (pair.getLeft().equals("(age<?)"));
        assert (pair.getRight().size() == 1);
    }

    @Test
    public void testGetInsertSqlByObj_ObjIsNull() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getInsertSqlByObj(null);
        assert (pair.getLeft().isEmpty());
    }

    @Test (expected = Bean2SqlException.class)
    public void testGetInsertSqlByObj_ObjIsEmpty() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getInsertSqlByObj(new TestObj());
        assert (pair.getLeft().isEmpty());
    }

    @Test
    public void testGetInsertSqlByObj_ObjIsOk() {
        TestObj obj = new TestObj();
        obj.setAge(1);
        obj.setName("hello");

        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getInsertSqlByObj(obj);
        assert (!pair.getLeft().isEmpty());
    }

    @Test
    public void testCheckEqualFieldType_Equal() {
        assert (Bean2SqlUtils.checkEqualFieldType("Long"));
    }

    @Test
    public void testCheckEqualFieldType_NotEqual() {
        assert (!Bean2SqlUtils.checkEqualFieldType("a"));
    }

    @Test
    public void testCheckComparableFieldType_Equal() {
        assert (Bean2SqlUtils.checkEqualFieldType("Long"));
    }

    @Test
    public void testCheckComparableFieldType_NotEqual() {
        assert (!Bean2SqlUtils.checkEqualFieldType("a"));
    }
}
