package com.devcourse.global.console;

import com.devcourse.voucher.domain.Voucher;
import com.devcourse.global.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class IoParser {
    private static final Logger logger = LoggerFactory.getLogger(IoParser.class);
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String NOT_SUPPORT_DATE_FORMAT = "Your Input is incorrect Date Format : ";
    private static final String NOT_SUPPORT_COMMAND = "Your Input Is Not Support. Command : ";
    private static final String NOT_SUPPORT_TYPE = "Your Input Is Not Support. Type : ";
    private static final String NOT_A_NUMBER = "Your Input is not a Number : ";

    public Command parseCommand(String command) {
        try {
            return Enum.valueOf(Command.class, command.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error(NOT_SUPPORT_COMMAND + command);
            throw new IllegalArgumentException(NOT_SUPPORT_COMMAND + command);
        }
    }

    public Voucher.Type parseVoucherType(String type) {
        try {
            return Enum.valueOf(Voucher.Type.class, type.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error(NOT_SUPPORT_TYPE + type);
            throw new IllegalArgumentException(NOT_SUPPORT_TYPE + type);
        }
    }

    public LocalDateTime parseExpiration(String expiredAt) {
        try {
            LocalDate localDate = LocalDate.parse(expiredAt, TIME_FORMAT);
            return LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
        } catch (DateTimeParseException e) {
            logger.error(NOT_SUPPORT_DATE_FORMAT + expiredAt);
            throw new IllegalArgumentException(NOT_SUPPORT_DATE_FORMAT + expiredAt);
        }
    }

    public Integer parseDiscount(String discount) {
        try {
            return Integer.parseInt(discount);
        } catch (IllegalArgumentException e) {
            logger.error(NOT_A_NUMBER + discount);
            throw new IllegalArgumentException(NOT_A_NUMBER + discount);
        }
    }
}
