package com.github.panhongan.bean2sql;

import com.github.panhongan.bean2sql.Bean2SqlUtils;
import com.github.panhongan.bean2sql.TestObj;
import com.github.panhongan.bean2sql.condition.SqlConditionOperator;
import com.github.panhongan.commons.MysqlConveyerException;
import com.github.panhongan.utils.time.DateUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

/**
 * @author panhongan
 * @since 2020.7.15
 * @version 1.0
 */

public class Bean2SqlUtilsTest {

    @Test
    public void testGetEqualTypeConditionSql_ConditionObjectIsNull() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getEqualTypeConditionSql(null, SqlConditionOperator.EQUAL);
        assert(pair.getLeft().isEmpty());
    }

    @Test
    public void testGetEqualTypeConditionSql_ConditionOperatorIsNotEqualType() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getEqualTypeConditionSql(new TestObj(), null);
        assert(pair.getLeft().isEmpty());
    }

    @Test
    public void testGetEqualTypeConditionSql_ConditionObjectIsNotSet() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getEqualTypeConditionSql(new TestObj(), SqlConditionOperator.EQUAL);
        assert(pair.getLeft().isEmpty());
    }

    @Test
    public void testGetEqualTypeConditionSql_ConditionObjectIsOk() {
        TestObj obj = new TestObj();
        obj.setAge(1);
        obj.setName("hello");

        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getEqualTypeConditionSql(obj, SqlConditionOperator.EQUAL);
        assert(!pair.getLeft().isEmpty());
        assert(pair.getLeft().equals("(name=? and age=?)"));
        assert(pair.getRight().size() == 2);
    }

    @Test
    public void testGetComparableConditionSql_ConditionObjectIsNull() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getComparableConditionSql(null, SqlConditionOperator.EQUAL);
        assert(pair.getLeft().isEmpty());
    }

    @Test
    public void testGetComparableConditionSql_ConditionOperatorIsNotComparableType() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getComparableConditionSql(new TestObj(), null);
        assert(pair.getLeft().isEmpty());
    }

    @Test
    public void testGetComparableConditionSql_ConditionObjectIsNotSet() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getComparableConditionSql(new TestObj(), SqlConditionOperator.LESS);
        assert(pair.getLeft().isEmpty());
    }

    @Test
    public void testGetComparableConditionSql_ConditionObjectIsOk() {
        TestObj obj = new TestObj();
        obj.setAge(1);
        obj.setName("hello");
        obj.setDate(DateUtils.plusDaysFromNow(-1));

        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getComparableConditionSql(obj, SqlConditionOperator.LESS);
        assert(!pair.getLeft().isEmpty());
        assert(pair.getLeft().equals("(age<? and date<?)"));
        assert(pair.getRight().size() == 2);
    }

    @Test
    public void testGetBetweenAndConditionSql_FieldNameIsEmpty() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getBetweenAndConditionSql(null, 1, 2);
        assert(pair.getLeft().isEmpty());
    }

    @Test
    public void testGetBetweenAndConditionSql_BeginIsEmpty() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getBetweenAndConditionSql("age", null, 2);
        assert(pair.getLeft().isEmpty());
    }

    @Test
    public void testGetBetweenAndConditionSql_EndIsEmpty() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getBetweenAndConditionSql("age", 1, null);
        assert(pair.getLeft().isEmpty());
    }

    @Test
    public void testGetBetweenAndConditionSql_Ok() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getBetweenAndConditionSql("age", 1, 2);
        assert(pair.getLeft().equals("(age between ? and ?)"));
        assert(pair.getRight().size() == 2);
    }

    @Test
    public void testGetLikeConditionSql_ObjIsNull() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getLikeConditionSql(null);
        assert(pair.getLeft().isEmpty());
    }

    @Test
    public void testGetLikeConditionSql_NoStringOrDateField() {
        TestObj testObj = new TestObj();
        testObj.setAge(100);
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getLikeConditionSql(testObj);
        assert(pair.getLeft().isEmpty());
    }

    @Test
    public void testGetLikeConditionSql_Ok() {
        TestObj testObj = new TestObj();
        testObj.setDate(new Date());
        testObj.setName("hello");
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getLikeConditionSql(testObj);
        assert(pair.getLeft().equals("(name like ? and date like ?)"));
        assert(pair.getRight().size() == 2);
    }

    @Test
    public void testGetInsertSqlByObj_ObjIsNull() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getInsertSqlByObj(null);
        assert(pair.getLeft().isEmpty());
    }

    @Test (expected = MysqlConveyerException.class)
    public void testGetInsertSqlByObj_ObjIsEmpty() {
        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getInsertSqlByObj(new TestObj());
        assert(pair.getLeft().isEmpty());
    }

    @Test
    public void testGetInsertSqlByObj_ObjIsOk() {
        TestObj obj = new TestObj();
        obj.setAge(1);
        obj.setName("hello");

        Pair<String, Map<Integer, String>> pair = Bean2SqlUtils.getInsertSqlByObj(obj);
        assert(!pair.getLeft().isEmpty());
    }

    @Test
    public void testCheckEqualFieldType_Equal() {
        assert(Bean2SqlUtils.checkEqualFieldType("Long"));
    }

    @Test
    public void testCheckEqualFieldType_NotEqual() {
        assert(!Bean2SqlUtils.checkEqualFieldType("a"));
    }

    @Test
    public void testCheckComparableFieldType_Equal() {
        assert(Bean2SqlUtils.checkEqualFieldType("Long"));
    }

    @Test
    public void testCheckComparableFieldType_NotEqual() {
        assert(!Bean2SqlUtils.checkEqualFieldType("a"));
    }
}
