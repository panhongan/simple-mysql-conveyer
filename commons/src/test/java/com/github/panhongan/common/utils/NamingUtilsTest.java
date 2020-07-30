package com.github.panhongan.common.utils;

import org.junit.Test;

/**
 * @author panhongan
 * @since 2019.7.14
 * @version 1.0
 */

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
