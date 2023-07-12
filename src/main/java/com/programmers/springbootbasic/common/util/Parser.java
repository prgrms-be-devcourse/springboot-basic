package com.programmers.springbootbasic.common.util;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Parser {
    private static final Long ZERO = 0L;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String END_OF_DAY = "23:59:59.999";
    private static final String INVALID_DATETIME = "잘못된 날짜/시간입니다. 현재 입력 값: ";
    private static final String INVALID_NUMBER = "잘못된 수입니다. 현재 입력 값: ";

    public Long parseToMinimumPriceCondition(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            return ZERO;
        }
    }

    public int parseToAmountOrPercent(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_NUMBER + input);
        }
    }

    public LocalDateTime parseToLocalDateTime(String input) {
        if (Validator.isDate(input)) {
            return dateParseToLocalDateTime(input);
        }
        if (Validator.isDateTime(input)) {
            return datetimeParseToLocalDateTime(input);
        }
        throw new IllegalArgumentException(INVALID_DATETIME + input);
    }

    private LocalDateTime dateParseToLocalDateTime(String date) {
        return datetimeParseToLocalDateTime(String.format("%s %s", date, END_OF_DAY));
    }

    private LocalDateTime datetimeParseToLocalDateTime(String datetime) {
        try {
            return LocalDateTime.parse(datetime, dateTimeFormatter);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(INVALID_DATETIME + datetime);
        }
    }
}
