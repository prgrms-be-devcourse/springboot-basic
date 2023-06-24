package org.weekly.weekly.voucher.exception;

import org.weekly.weekly.util.ExceptionMsg;

import java.time.LocalDate;

public class VoucherException {

    private static boolean isNumber(String userInput) {
        return userInput.chars().allMatch(value -> Character.isDigit(value));
    }
    public static void notNumberFormat(String amount) {
        if (!isNumber(amount)) {
            throw new RuntimeException(ExceptionMsg.NOT_NUMBER_FORMAT.getMsg());
        }
    }

    public static void expirationError(LocalDate registrationDate, String expirationMonth) {
        LocalDate expirationDate = registrationDate.plusMonths(Long.parseLong(expirationMonth));

        if (registrationDate.isAfter(expirationDate)) {
            throw new RuntimeException(ExceptionMsg.EXPIRATION_ERROR.getMsg());
        }
    }
}