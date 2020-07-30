package com.github.panhongan.condition.commons;

import com.github.panhongan.condition.common.DateUtils;
import org.junit.Test;

import java.util.Date;

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
