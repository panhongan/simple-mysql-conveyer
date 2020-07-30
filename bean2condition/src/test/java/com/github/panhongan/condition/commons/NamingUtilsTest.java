package com.github.panhongan.condition.commons;

import com.github.panhongan.condition.common.NamingUtils;
import org.junit.Test;

public class NamingUtilsTest {

    @Test
    public void testCamel2Hung_Empty() {
        assert (NamingUtils.camel2Hung("") == null);
    }

    @Test
    public void testCamel2Hung_Ok() {
        assert (NamingUtils.camel2Hung("orgCode").equals("org_code"));
    }

    @Test
    public void testHung2Camel_Empty() {
        assert (NamingUtils.hung2Camel("") == null);
    }

    @Test
    public void test2Hung2Camel_Ok() {
        assert (NamingUtils.hung2Camel("org_codeca").equals("orgCodeca"));
    }
}
