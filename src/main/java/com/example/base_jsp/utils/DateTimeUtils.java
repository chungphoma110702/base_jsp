package com.example.base_jsp.utils;


import com.example.base_jsp.aop.exceptions.ApiException;
import com.example.base_jsp.aop.exceptions.ERROR;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Objects;

public class DateTimeUtils {
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String ISO_DATETIME_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss";
    public static DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
    public static final int MINUTES_TO_ROUND = 10;

    public static final DateTimeFormatter DATE_FORMATTER_AMPM = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

    public static DateTimeFormatter SNAKE_FORMAT = DateTimeFormatter.ofPattern("yyyy_MM_dd");

    public static Long roundTime(Long timestamp) {
        Date date = new Date(timestamp);

        // Calculate the rounded minutes to the nearest 10 minutes
        int minutes = date.getMinutes();
        int roundedMinutes = Math.round((float) minutes / MINUTES_TO_ROUND) * MINUTES_TO_ROUND;

        date.setMinutes(roundedMinutes);
        date.setSeconds(0);
        return (date.getTime() / 1000) * 1000;
    }

    public static Long roundCurrentTime() {
        return roundTime(new Date().getTime());
    }


    public static String convertDateTimeToDateFormat(LocalDateTime time, String dateFormat) {
        LocalDate date = time.toLocalDate();

        return date.format(DateTimeFormatter.ofPattern(dateFormat));
    }


    public static String convertDateTimeFormaterAMPM(LocalDateTime time) {
        return time.format(DATE_FORMATTER_AMPM);
    }

    public static String convertISODate(LocalDateTime localDateTime) {
        return localDateTime.format(DEFAULT_DATE_FORMATTER);
    }

    public static String convertLocalDate(LocalDate localDate, String format) {

        return localDate.format(DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime convertISODate(String date) {
        return LocalDateTime.parse(date, DEFAULT_DATE_FORMATTER);
    }

    public static long convertToTimestamp(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        // Chuyển đổi ZonedDateTime sang Instant
        Instant instant = zonedDateTime.toInstant();


        return instant.toEpochMilli();
    }

    public static long convertToTimestampSecond(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        // Chuyển đổi ZonedDateTime sang Instant
        Instant instant = zonedDateTime.toInstant();


        return instant.toEpochMilli() / 1000;
    }

    public static long convertToTimestampSecondUCT(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneOffset.UTC);

        // Chuyển đổi ZonedDateTime sang Instant
        Instant instant = zonedDateTime.toInstant();


        return instant.toEpochMilli() / 1000;
    }

    public static LocalDateTime getStartOfDay(LocalDateTime localDateTime) {

        return localDateTime.with(LocalTime.MIDNIGHT);
    }

    public static LocalDateTime getStartOfJackpot(LocalDateTime time) {

        // Tìm thứ Hai của tuần hiện tại

        return time.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).withHour(12).withMinute(0).withSecond(0).withNano(0);

    }

    public static LocalDateTime getStartOfJackpotMonthly(LocalDateTime time) {

        // Tìm thứ Hai của tuần hiện tại

        return time.withDayOfMonth(1).withHour(12).withMinute(0).withSecond(0).withNano(0);

    }


    public static String convertSnakeFormat(LocalDateTime time) {
        return time.format(SNAKE_FORMAT);
    }

    public static String convertTimeFromDBToDDMMYYYY(LocalDateTime input) {
        if (input == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return input.format(formatter);
    }

    public static LocalDateTime convertTimeFromDDMMYYYYToDB(String input) {
        if (StringUtils.isBlank(input)) return null;
        try {
            return LocalDateTime.parse(input + " 00:00:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        } catch (DateTimeParseException e) {
            throw new ApiException(ERROR.BAD_REQUEST.getCode(), "Ngày không đúng định dạng dd/MM/yyyy: " + input);
        }
    }

    public static LocalDateTime convertStringToLocalDateTime(String input) {
        if (StringUtil.isNullOrEmpty(input)) {
            return null;
        }
        try {
            return LocalDateTime.parse(input, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Sai định dạng ngày tháng năm. Mong muốn định dạng kiểu ISO (e.g. 2025-05-17T15:30:00)", e);
        }
    }

    public static String convertLocalDateTimeToString(LocalDateTime input) {
        if (Objects.isNull(input)) {
            return "";
        }
        try {
            return input.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Sai định dạng ngày tháng năm. Mong muốn định dạng kiểu ISO (e.g. 2025-05-17T15:30:00)", e);
        }
    }

    public static LocalDateTime getStartDate(Integer month, int year) {
        return (month != null)
                ? LocalDate.of(year, month, 1).atStartOfDay()
                : LocalDate.of(year, 1, 1).atStartOfDay();
    }

    public static LocalDateTime getEndDate(Integer month, int year) {
        return (month != null)
                ? getStartDate(month, year).plusMonths(1).minusDays(1).with(LocalTime.MAX)
                : LocalDate.of(year, 12, 31).atTime(LocalTime.MAX);
    }


    public static void main(String[] args) {

        System.out.println(convertISODate("2025-03-29T03:57:54.497"));
    }
}
