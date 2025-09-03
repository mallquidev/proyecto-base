package com.mllq.base.proyecto_base.core.commons.models;

import lombok.Data;

@Data
public class Time {
    private long value;
    private TimeUnit unit;

    public static Time of(long value, TimeUnit unit) {
        Time time = new Time();
        time.setValue(value);
        time.setUnit(unit);
        return time;
    }
    public static Time ofMilliseconds(long milliseconds) {
        return of(milliseconds, TimeUnit.MILLISECONDS);
    }
    public static Time ofSeconds(long seconds) {
        return of(seconds, TimeUnit.SECONDS);
    }
    public static Time ofMinutes(long minutes) {
        return of(minutes, TimeUnit.MINUTES);
    }
    public static Time ofHours(long hours) {
        return of(hours, TimeUnit.HOURS);
    }
    public static Time ofDays(long days) {
        return of(days, TimeUnit.DAYS);
    }
    public static Time ofWeeks(long weeks) {
        return of(weeks, TimeUnit.WEEKS);
    }
    public static Time ofMonths(long months) {
        return of(months, TimeUnit.MONTHS);
    }
    public static Time ofYears(long years) {
        return of(years, TimeUnit.YEARS);
    }
    public enum TimeUnit {
        MILLISECONDS,
        SECONDS,
        MINUTES,
        HOURS,
        DAYS,
        WEEKS,
        MONTHS,
        YEARS
    }
}