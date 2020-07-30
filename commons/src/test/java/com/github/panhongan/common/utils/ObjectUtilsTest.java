package com.github.panhongan.common.utils;

import com.github.panhongan.common.Bean2SqlException;
import com.github.panhongan.common.TestObj;
import org.junit.Test;

/**
 * @author panhongan
 * @since 2019.7.14
 * @version 1.0
 */

public class ObjectUtilsTest {

    @Test (expected = Bean2SqlException.class)
    public void testValidateObject_Exception() {
        ObjectUtils.validateObject(new TestObj());
    }

    @Test
    public void testValidateObject_Ok() {
        TestObj obj = new TestObj();
        obj.name = "hello";
        ObjectUtils.validateObject(obj);
    }
}
