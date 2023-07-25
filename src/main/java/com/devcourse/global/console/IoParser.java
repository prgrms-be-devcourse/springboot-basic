package com.devcourse.global.console;

import com.devcourse.global.Command;
import com.devcourse.voucher.domain.Voucher;
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
    private static final String NEGATIVE_DISCOUNT = "Discount Value MUST Be Positive. Input : ";
    private static final String OUT_RANGED_DISCOUNT = "Discount Rate MUST Be Smaller Than 100. Input : ";
    private static final String INVALID_EXPIRATION = "Expiration Time Cannot Be The Past. Input : ";
    private static final int MAX_DISCOUNT_RATE = 100;
    private static final int MIN_DISCOUNT = 0;

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

    public LocalDateTime toValidExpiredAt(String expiredAt) {
        LocalDateTime parsedExpiredAt = parseExpiration(expiredAt);
        validateExpiration(parsedExpiredAt);
        return parsedExpiredAt;
    }

    public int toValidDiscountByType(Voucher.Type type, String discount) {
        int parsedDiscount = parseDiscount(discount);
        validateDiscount(type, parsedDiscount);
        return parsedDiscount;
    }

    private LocalDateTime parseExpiration(String expiredAt) {
        try {
            LocalDate localDate = LocalDate.parse(expiredAt, TIME_FORMAT);
            return LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
        } catch (DateTimeParseException e) {
            logger.error(NOT_SUPPORT_DATE_FORMAT + expiredAt);
            throw new IllegalArgumentException(NOT_SUPPORT_DATE_FORMAT + expiredAt);
        }
    }

    private Integer parseDiscount(String discount) {
        try {
            return Integer.parseInt(discount);
        } catch (IllegalArgumentException e) {
            logger.error(NOT_A_NUMBER + discount);
            throw new IllegalArgumentException(NOT_A_NUMBER + discount);
        }
    }

    private void validateDiscount(Voucher.Type type, int discount) {
        if (isNegative(discount)) {
            logger.error(NEGATIVE_DISCOUNT + discount);
            throw new IllegalArgumentException(NEGATIVE_DISCOUNT + discount);
        }

        if (type.isPercent() && isRateOutRange(discount)) {
            logger.error(OUT_RANGED_DISCOUNT + discount);
            throw new IllegalArgumentException(OUT_RANGED_DISCOUNT + discount);
        }
    }

    private void validateExpiration(LocalDateTime expiredAt) {
        LocalDateTime now = LocalDateTime.now();

        if (expiredAt.isBefore(now)) {
            logger.error(INVALID_EXPIRATION + expiredAt);
            throw new IllegalArgumentException(INVALID_EXPIRATION + expiredAt);
        }
    }

    private boolean isNegative(int discountAmount) {
        return discountAmount <= MIN_DISCOUNT;
    }

    private boolean isRateOutRange(int discountRate) {
        return MAX_DISCOUNT_RATE < discountRate;
    }
}
