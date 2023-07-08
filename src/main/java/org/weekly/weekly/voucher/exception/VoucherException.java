package org.weekly.weekly.voucher.exception;

import org.weekly.weekly.util.ExceptionMsg;

import java.time.LocalDate;
import java.util.function.Predicate;

public class VoucherException {
    private static boolean isNumber(String userInput) {
        return userInput.chars().allMatch(value -> Character.isDigit(value));
    }

    private static void notNumber(String userInput) {
        if (!isNumber(userInput)) {
            throw new RuntimeException(ExceptionMsg.NOT_NUMBER_FORMAT.getMsg());
        }
    }

    private static void notRange(String userInput, Predicate<String> ifCase) {
        if (ifCase.test(userInput)) {
            throw new RuntimeException(ExceptionMsg.NOT_NUMBER_FORMAT.getMsg());
        }
    }

    public static void notNumberFormat(String userInput, Predicate<String> ifCase) {
        notNumber(userInput);
        notRange(userInput, ifCase);
    }

    public static void expirationError(LocalDate registrationDate, String expirationMonth) {
        LocalDate expirationDate = registrationDate.plusMonths(Long.parseLong(expirationMonth));

        if (registrationDate.isEqual(expirationDate) || registrationDate.isAfter(expirationDate)) {
            throw new RuntimeException(ExceptionMsg.EXPIRATION_ERROR.getMsg());
        }
    }
}