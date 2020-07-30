package com.github.panhongan.common.utils;

import org.junit.Test;
import java.util.Date;

/**
 * @author panhongan
 * @since 2019.7.13
 * @version 1.0
 */

public class DateUtilsTest {

    @Test (expected = NullPointerException.class)
    public void testFormat_DateIsNull() {
        DateUtils.format(null, "");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testFormat_PatternIsEmpty() {
        DateUtils.format(new Date(), "");
    }

    @Test
    public void testFormat_Ok() {
        assert (DateUtils.format(new Date(), DateUtils.SETTLE_PATTERN) != null);
    }
}
