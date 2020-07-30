package com.github.panhongan.common.utils;

import java.util.Date;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

/**
 * @author panhongan
 * @since 2019.7.13
 * @version 1.0
 */

public class DateUtils {

    public static final String SETTLE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date, String pattern) {
        Preconditions.checkNotNull(date);
        Preconditions.checkArgument(StringUtils.isNotEmpty(pattern));

        return new DateTime(date).toString(pattern);
    }
}
