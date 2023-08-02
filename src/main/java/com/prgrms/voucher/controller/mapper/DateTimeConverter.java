package com.prgrms.voucher.controller.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class DateTimeConverter {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd");

    public LocalDateTime parseToDate(String createdAt) {
        if (createdAt == null || createdAt.trim().isEmpty()) {
            return null;
        }

        return LocalDate.parse(createdAt, DATE_TIME_FORMATTER).atStartOfDay();
    }

}
