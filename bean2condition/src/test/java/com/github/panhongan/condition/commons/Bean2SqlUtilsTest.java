package com.github.panhongan.condition.commons;

import com.github.panhongan.condition.ConditionOperator;
import com.github.panhongan.condition.TestObj;
import com.github.panhongan.condition.common.Bean2SqlUtils;
import org.junit.Test;

public class Bean2SqlUtilsTest {

    @Test
    public void testGetEqualTypeConditionSql_ConditionObjectIsNull() {
        assert (Bean2SqlUtils.getEqualTypeConditionSql(null, ConditionOperator.EQUAL).getLeft().isEmpty());
    }

    @Test
    public void testGetEqualTypeConditionSql_ConditionOperatorIsNotEqualType() {
        assert (Bean2SqlUtils.getEqualTypeConditionSql(new TestObj(), null).getLeft().isEmpty());
    }

    @Test
    public void testGetEqualTypeConditionSql_ConditionObjectIsNotSet() {
        assert (Bean2SqlUtils.getEqualTypeConditionSql(new TestObj(), ConditionOperator.EQUAL).getLeft().isEmpty());
    }

    @Test
    public void testGetEqualTypeConditionSql_ConditionObjectIsOk() {
        TestObj obj = new TestObj();
        obj.setAge(1);
        obj.setName("hello");
        assert (!Bean2SqlUtils.getEqualTypeConditionSql(obj, ConditionOperator.EQUAL).getLeft().isEmpty());
    }


}
