package com.imchat.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 */
public final class LocalDateTimeHelper {

    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Shanghai"));
    }

    public static LocalDateTime longToLocalDateTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return dateToLocalDateTime(new Date(timestamp));
    }

    public static LocalDateTime parse(String str) {
        return ZonedDateTime.parse(str).withZoneSameInstant(ZoneId.of("Asia/Shanghai")).toLocalDateTime();
    }

    public static LocalDateTime max(LocalDateTime time1, LocalDateTime time2) {
        if (time1 == null) {
            return time2;
        }
        if (time2 == null) {
            return time1;
        }

        return time1.isAfter(time2) ? time1 : time2;
    }

    public static Long toLong(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }


    public static String toStr(LocalDateTime localDateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Shanghai"));
        return formatter.format(localDateTime);
    }

}
