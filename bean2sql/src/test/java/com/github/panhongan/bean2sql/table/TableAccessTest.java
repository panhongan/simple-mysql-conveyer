package com.github.panhongan.bean2sql.table;

import com.github.panhongan.bean2sql.condition.SqlConditionMaker;
import com.github.panhongan.bean2sql.TestObj;
import com.github.panhongan.commons.MysqlConveyerException;
import com.github.panhongan.bean2sql.condition.impl.EqualCondition;
import com.github.panhongan.bean2sql.condition.SqlCondition;
import com.github.panhongan.spring.utils.InjectUtils;
import com.github.panhongan.spring.test.SpringTest;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Map;

/**
 * @author panhongan
 * @since 2019.7.15
 * @version 1.0
 */

public class TableAccessTest extends SpringTest {

    @InjectMocks
    private MyTableAccess tableAccess = new MyTableAccess();

    @Mock
    private SqlExecutor sqlExecutor;

    @Override
    protected void inject() {
        InjectUtils.inject(tableAccess, sqlExecutor);
    }

    @Test
    public void testGetMaxRowId_SqlConditionIsNull() {
        tableAccess.getMaxRowId(null);
    }

    @Test
    public void testGetMaxRowId_SqlConditionIsNotNull() {
        TestObj obj = new TestObj();
        obj.setName("hello");

        EqualCondition condition = SqlConditionMaker.equalCondition(obj);

        Mockito.when(sqlExecutor.getMaxRowId(Mockito.anyString(), Mockito.anyMap())).thenReturn(1L);
        assert(tableAccess.getMaxRowId(condition) == 1L);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInsert_RecordIsNull() {
        tableAccess.insert(null);
    }

    @Test
    public void testInsert_RecordIsOk() {
        TestObj obj = new TestObj();
        obj.setName("hello");
        obj.setAge(1);
        obj.setHeight(1.0f);
        obj.setWeight(2.0d);

        Mockito.when(sqlExecutor.insert(Mockito.anyString(), Mockito.anyMap())).thenReturn(Collections.singletonList(1L));
        assert(tableAccess.insert(obj) == 1L);
    }

    @Test
    public void testDeleteById_Ok() {
        Mockito.when(sqlExecutor.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        assert(tableAccess.deleteById(1L) == 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testUpdate_RecordIsNull() {
        tableAccess.update(1L, null);
    }

    @Test (expected = MysqlConveyerException.class)
    public void testUpdate_RecordIsEmpty() {
        TestObj obj = new TestObj();
        tableAccess.update(1, obj);
    }

    @Test
    public void testUpdate_RecordIsOk() {
        TestObj obj = new TestObj();
        obj.setName("hello");
        obj.setAge(1);
        obj.setHeight(1.0f);
        obj.setWeight(2.0d);

        Mockito.when(sqlExecutor.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        assert(tableAccess.update(1, obj) == 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testQueryByCondition_ObjectConditionIsNull() {
        tableAccess.queryByCondition(null);
    }

    @Test
    public void testQueryByCondition_ObjectConditionIsOk() {
        TestObj obj = new TestObj();
        obj.setName("hello");
        Mockito.when(sqlExecutor.select(Mockito.anyString(), Mockito.anyMap(), Mockito.any())).thenReturn(Collections.emptyList());
        assert(tableAccess.queryByCondition(obj).isEmpty());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testQueryByCondition_ObjectAndSqlCondition_ObjectConditionIsNull() {
        tableAccess.queryByCondition(null, (SqlCondition) null);
    }

    @Test
    public void testQueryByCondition_ObjectAndSqlCondition_Ok() {
        TestObj obj = new TestObj();
        obj.setName("hello");

        TestObj obj1 = new TestObj();
        obj1.setAge(10);

        Mockito.when(sqlExecutor.select(Mockito.anyString(), Mockito.anyMap(), Mockito.any())).thenReturn(Collections.emptyList());
        assert(tableAccess.queryByCondition(obj, SqlConditionMaker.equalCondition(obj1)).isEmpty());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testQueryByCondition_SqlConditionIsNull() {
        tableAccess.queryByCondition(null, TestObj.class);
    }

    @Test
    public void testQueryByCondition_SqlConditionIsOk() {
        TestObj obj = new TestObj();
        obj.setName("hello");

        Mockito.when(sqlExecutor.select(Mockito.anyString(), Mockito.anyMap(), Mockito.any())).thenReturn(Collections.emptyList());
        assert(tableAccess.queryByCondition(SqlConditionMaker.equalCondition(obj), TestObj.class).isEmpty());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testQueryByPage_ObjectConditionIsNull() {
        tableAccess.queryByPage(null, new PageContext());
    }

    @Test
    public void testQueryByPage_ObjectConditionIsOk() {
        TestObj obj = new TestObj();
        obj.setName("hello");

        PageContext pageContext = new PageContext();
        pageContext.setCurrPage(1);
        pageContext.setPageSize(10);

        Mockito.when(sqlExecutor.select(Mockito.anyString(), Mockito.anyMap(), Mockito.any())).thenReturn(Collections.emptyList());
        assert(tableAccess.queryByPage(obj, pageContext).isEmpty());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testQueryByPage_ObjectAndSqlCondition_ObjectConditionIsNull() {
        tableAccess.queryByPage(null, null, (PageContext) null);
    }

    @Test
    public void testQueryByPage_ObjectAndSqlCondition_Ok() {
        TestObj obj = new TestObj();
        obj.setName("hello");

        TestObj obj1 = new TestObj();
        obj1.setAge(10);

        PageContext pageContext = new PageContext();
        pageContext.setCurrPage(1);
        pageContext.setPageSize(10);

        Mockito.when(sqlExecutor.select(Mockito.anyString(), Mockito.anyMap(), Mockito.any())).thenReturn(Collections.emptyList());
        assert(tableAccess.queryByPage(obj, SqlConditionMaker.equalCondition(obj1), pageContext).isEmpty());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testQueryByPage_SqlConditionIsNull() {
        tableAccess.queryByPage(null, null, TestObj.class);
    }

    @Test
    public void testQueryByPage_SqlConditionIsOk() {
        TestObj obj = new TestObj();
        obj.setName("hello");

        PageContext pageContext = new PageContext();
        pageContext.setCurrPage(1);
        pageContext.setPageSize(10);

        Mockito.when(sqlExecutor.select(Mockito.anyString(), Mockito.anyMap(), Mockito.any())).thenReturn(Collections.emptyList());
        assert(tableAccess.queryByPage(SqlConditionMaker.equalCondition(obj), pageContext, TestObj.class).isEmpty());
    }

    @Test
    public void testMakeAndCondition_Ok() {
        TestObj obj = new TestObj();
        obj.setName("hello");

        TestObj obj1 = new TestObj();
        obj1.setAge(10);

        SqlCondition sqlCondition = tableAccess.makeAndCondition(obj, SqlConditionMaker.equalCondition(obj1));
        Pair<String, Map<Integer, String>> pair = sqlCondition.conditionSql();
        assert(pair.getLeft().contains("name") && pair.getLeft().contains("age"));
        assert(pair.getRight().size() == 2);
    }

    private static class MyTableAccess extends AbstractTableAccess<TestObj> {

        private static final String TABLE_NAME = "t_person";

        @Override
        public String getTable() {
            return TABLE_NAME;
        }

        @Override
        public TestObj emptyDO() {
            return new TestObj();
        }
    }
}
