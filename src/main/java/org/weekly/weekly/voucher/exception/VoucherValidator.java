package org.weekly.weekly.voucher.exception;

import org.weekly.weekly.util.ExceptionMsg;
import org.weekly.weekly.voucher.domain.DiscountType;

import java.time.LocalDate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

public class VoucherValidator {
    private static final int RANGE_START = 0;
    private static final int RANGE_END = 100;

    private VoucherValidator() {
        throw new VoucherException(ExceptionMsg.UTIL_CLASS);
    }

    public static void validateExpiration(LocalDate registrationDate, long expirationMonth) {
        LocalDate expirationDate = registrationDate.plusMonths(expirationMonth);

        if (registrationDate.isEqual(expirationDate) || registrationDate.isAfter(expirationDate)) {
            throw new VoucherException(ExceptionMsg.EXPIRATION_ERROR);
        }
    }
    public static void validateAmount(DiscountType discountType, long amount) {
        if (DiscountType.FIXED.equals(discountType)) {
            notRange(amount, input -> input < RANGE_START);
        }

        notRange(amount, input -> input < RANGE_START || input > RANGE_END);
    }

    private static void notRange(long userInput, LongPredicate ifCase) {
        if (ifCase.test(userInput)) {
            throw new VoucherException(ExceptionMsg.NOT_NUMBER_FORMAT);
        }
    }
}
