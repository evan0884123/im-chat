package com.imchat.common.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

public class TimeUtil {

    public static Long getFirstDayOfWeek() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli());
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        return calendar.getTimeInMillis();
    }

    public static Long getLastDayOfWeek() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli());
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 6);
        return calendar.getTimeInMillis();
    }

    public static Long getCurrentMonthFirstDay() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli());
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
        return calendar.getTimeInMillis();
    }

    public static Long getCurrentMonthLastDay() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli());
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTimeInMillis();
    }
}
