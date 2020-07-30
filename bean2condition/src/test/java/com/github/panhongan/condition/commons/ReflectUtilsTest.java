package com.github.panhongan.condition.commons;

import com.github.panhongan.condition.TestObj;
import com.github.panhongan.condition.common.ReflectUtils;
import org.junit.Test;

public class ReflectUtilsTest {

    @Test (expected = NullPointerException.class)
    public void testGetClassBeanFieldFast_Exception() {
        ReflectUtils.getClassBeanFieldFast(null);
    }

    @Test
    public void testGetClassBeanFieldFast_Ok() {
        assert (ReflectUtils.getClassBeanFieldFast(TestObj.class).size() == 4);
    }
}
