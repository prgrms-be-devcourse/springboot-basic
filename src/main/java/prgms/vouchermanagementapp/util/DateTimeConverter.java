package prgms.vouchermanagementapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeConverter {

    private static final Logger log = LoggerFactory.getLogger(DateTimeConverter.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yy-MM-dd");

    private DateTimeConverter() {
    }

    public static LocalDateTime toStartOfDay(String date) {
        return toLocalDate(date).atStartOfDay();
    }

    public static LocalDateTime toEndOfDay(String date) {
        return toLocalDate(date).atTime(LocalTime.MAX);
    }

    private static LocalDate toLocalDate(String date) {
        try {
            return LocalDate.parse(date, FORMATTER);
        } catch (DateTimeParseException dateTimeParseException) {
            log.error("DateTimeParseException occurred: {}", dateTimeParseException.getMessage());
            throw new IllegalArgumentException(MessageFormat.format(
                    "''{0}'' is invalid date format.(yy-MM-dd)", date
            ));
        }
    }
}
