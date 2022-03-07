package com.github.panhongan.mysql.conveyer.bean2sql;

import com.github.panhongan.mysql.conveyer.bean2sql.condition.SqlConditionMaker;
import com.github.panhongan.mysql.conveyer.bean2sql.condition.impl.EqualCondition;
import com.github.panhongan.mysql.conveyer.bean2sql.orderby.OrderBy;
import com.github.panhongan.mysql.conveyer.bean2sql.table.PageContext;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.Map;

/**
 * @author panhongan
 * @since 2020.9.15
 * @version 1.0
 */

public class SqlMakerTest {

    private static final String TABLE_NAME = "t_person";

    @Test (expected = IllegalArgumentException.class)
    public void testMakeMaxRowIdSql_TableIsEmpty() {
        SqlMaker.makeMaxRowIdSql("", null);
    }

    @Test
    public void testMakeMaxRowIdSql_Ok() {
        TestObj obj = new TestObj();
        obj.setName("hello");

        EqualCondition condition = SqlConditionMaker.equalCondition(obj);
        Pair<String, Map<Integer, String>> pair = SqlMaker.makeMaxRowIdSql(TABLE_NAME, condition);

        assert(pair.getLeft().contentEquals("select max(id) from t_person where (name=?)"));
        assert(pair.getRight().size() == 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testMakeInsertSql_TableNameIsEmpty() {
        SqlMaker.makeInsertSql("", null);
    }

    @Test (expected = NullPointerException.class)
    public void testMakeInsertSql_SqlConditionIsNull() {
        SqlMaker.makeInsertSql("t_person", null);
    }

    @Test
    public void testMakeInsertSql_Ok() {
        TestObj obj = new TestObj();
        obj.setName("hello");
        obj.setAge(1);
        obj.setHeight(1.0f);
        obj.setWeight(2.0d);

        Pair<String, Map<Integer, String>> pair = SqlMaker.makeInsertSql(TABLE_NAME, obj);

        assert(pair.getLeft().contentEquals("insert into t_person(name,age,height,weight) values(?,?,?,?)"));
        assert(pair.getRight().size() == 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMakeDeleteByIdSql_TableNameIsEmpty() {
        SqlMaker.makeDeleteByIdSql("", 1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMakeDeleteByIdSql_InvalidId() {
        SqlMaker.makeDeleteByIdSql("t_person", 0L);
    }

    @Test
    public void testMakeDeleteByIdSql_Ok() {
        Pair<String, Map<Integer, String>> pair = SqlMaker.makeDeleteByIdSql("t_person", 1L);
        assert(pair.getLeft().equals("delete from t_person where id=?"));
        assert(pair.getRight().size() == 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testMakeUpdateSql_TableNameIsEmpty() {
        SqlMaker.makeUpdateSql("", 1L, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testMakeUpdateSql_InvalidId() {
        SqlMaker.makeUpdateSql("t_person", 0L, null);
    }

    @Test (expected = NullPointerException.class)
    public void testMakeUpdateSql_NewObjIsNull() {
        SqlMaker.makeUpdateSql("t_person", 1L, null);
    }

    @Test
    public void testMakeUpdateSql_Ok() {
        TestObj obj = new TestObj();
        obj.setName("hello");
        obj.setAge(1);
        obj.setHeight(1.0f);
        obj.setWeight(2.0d);

        Pair<String, Map<Integer, String>> pair = SqlMaker.makeUpdateSql(TABLE_NAME, 1L, obj);

        assert(pair.getLeft().contentEquals("update t_person set name=?,age=?,height=?,weight=? where id=?"));
        assert(pair.getRight().size() == 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMakeQueryByConditionSql_TableNameIsEmpty() {
        SqlMaker.makeQueryByConditionSql("", null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testMakeQueryByConditionSql_SqlConditionIsNull() {
        SqlMaker.makeQueryByConditionSql("t_person", null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testMakeQueryByConditionSql_ClassIsNull() {
        TestObj obj = new TestObj();
        obj.setName("hello");

        EqualCondition<TestObj> condition = SqlConditionMaker.equalCondition(obj);
        SqlMaker.makeQueryByConditionSql("t_person", condition, null, null);
    }

    @Test
    public void testMakeQueryByConditionSql_Ok() {
        TestObj obj = new TestObj();
        obj.setName("hello");

        EqualCondition<TestObj> condition = SqlConditionMaker.equalCondition(obj);

        OrderBy<OrderByObj> orderBy = new OrderBy(OrderByObj.class, false);

        Pair<String, Map<Integer, String>> pair = SqlMaker.makeQueryByConditionSql(TABLE_NAME, condition, orderBy, obj.getClass());

        assert(pair.getLeft().contentEquals("select name,age,height,weight,date from t_person where (name=?) order by id,name desc"));
        assert(pair.getRight().size() == 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMakeQueryByPageSql_TableNameIsEmpty() {
        SqlMaker.makeQueryByPageSql("", null, null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testMakeQueryByPageSql_SqlConditionIsNull() {
        SqlMaker.makeQueryByPageSql("t_person", null, null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testMakeQueryByCPageSql_PageContextIsNull() {
        TestObj obj = new TestObj();
        obj.setName("hello");

        EqualCondition<TestObj> condition = SqlConditionMaker.equalCondition(obj);
        SqlMaker.makeQueryByPageSql("t_person", condition, null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testMakeQueryByCPageSql_ClassIsNull() {
        TestObj obj = new TestObj();
        obj.setName("hello");

        PageContext pageContext = new PageContext();
        pageContext.setCurrPage(1);
        pageContext.setPageSize(10);

        EqualCondition<TestObj> condition = SqlConditionMaker.equalCondition(obj);
        SqlMaker.makeQueryByPageSql("t_person", condition, pageContext, null, null);
    }

    @Test
    public void testMakeQueryByPageSql_Ok() {
        TestObj obj = new TestObj();
        obj.setName("hello");

        EqualCondition<TestObj> condition = SqlConditionMaker.equalCondition(obj);

        PageContext pageContext = new PageContext();
        pageContext.setCurrPage(1);
        pageContext.setPageSize(10);

        OrderBy<OrderByObj> orderBy = new OrderBy(OrderByObj.class, false);

        Pair<Pair<String, String>, Map<Integer, String>> pair = SqlMaker.makeQueryByPageSql(TABLE_NAME, condition, pageContext, orderBy, obj.getClass());

        assert(pair.getLeft().getLeft().contentEquals("select count(id) from t_person where (name=?) order by id,name desc"));
        assert(pair.getLeft().getRight().contentEquals("select name,age,height,weight,date from t_person where (name=?) order by id,name desc limit 0,10"));
        assert(pair.getRight().size() == 1);
    }
}
