package com.github.panhongan.demo.thrift;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

/**
 * @author panhongan
 * @since 2020.9.10
 * @version 1.0
 */

public class TimeUtilsThrift {

    public static long date2timestamp(Date date) {
        if (Objects.isNull(date)) {
            return 0L;
        }

        return date.toInstant().toEpochMilli();
    }

    public static Date timestamp2date(long timestamp) {
        if (timestamp <= 0) {
            return null;
        }

        Instant instant = Instant.ofEpochMilli(timestamp);
        return Date.from(instant);
    }
}
