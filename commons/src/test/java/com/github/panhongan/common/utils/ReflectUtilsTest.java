package com.github.panhongan.common.utils;

import com.github.panhongan.common.TestObj;
import org.junit.Test;

/**
 * @author panhongan
 * @since 2019.7.14
 * @version 1.0
 */

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
