package com.github.panhongan.demo.proto;

import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

/**
 * @author panhongan
 * @since 2020.9.10
 * @version 1.0
 */

public class TimeUtilsProto {

    public static Timestamp date2timestamp(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }

        Instant instant = date.toInstant();
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    public static Date timestamp2date(Timestamp timestamp) {
        if (Objects.isNull(timestamp)) {
            return null;
        }

        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        return Date.from(instant);
    }
}
