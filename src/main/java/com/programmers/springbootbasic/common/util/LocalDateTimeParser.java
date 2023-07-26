package com.programmers.springbootbasic.common.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class LocalDateTimeParser {
    private static final String INVALID_DATETIME = "잘못된 날짜/시간입니다. 현재 입력 값: ";

    public static LocalDateTime parseToLocalDateTime(String input) {
        if (PatternUtils.isDate(input)) {
            return dateParseToLocalDateTime(input);
        }
        if (PatternUtils.isDateTime(input)) {
            return datetimeParseToLocalDateTime(input);
        }
        throw new IllegalArgumentException(INVALID_DATETIME + input);
    }

    private static LocalDateTime dateParseToLocalDateTime(String date) {
        try {
            return LocalDate.parse(date)
                    .atTime(LocalTime.MAX);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(INVALID_DATETIME + date);
        }
    }

    private static LocalDateTime datetimeParseToLocalDateTime(String datetime) {
        try {
            return LocalDateTime.parse(toIsoFormat(datetime), DateTimeFormatter.ISO_DATE_TIME);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(INVALID_DATETIME + datetime);
        }
    }

    private static String toIsoFormat(String dateTime) {
        return dateTime.replace(' ', 'T');
    }
}
