package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exception.IsNotNumberException;
import org.prgrms.kdt.exception.NotFindVoucherType;
import org.prgrms.kdt.exception.WrongRangeInputException;

import java.util.regex.Pattern;

public class VoucherValidator {
    public static final String NUMBER_REGEX = "^[\\+\\-]?\\d+$";
    private static final int MAX_PERCENT = 100;
    private static final long MAX_FIXED_AMOUNT = Long.MAX_VALUE;
    private static final int ZERO = 0;

    private VoucherValidator() {
    }

    public static void isNumeric(String number) {
        if (number == null) {
            throw new IllegalArgumentException();
        }
        if (!Pattern.matches(NUMBER_REGEX, number)) {
            throw new IsNotNumberException();
        }
    }

    private static void validateDiscountAmount(String discountValue) {
        isNumeric(discountValue);
        Long longDiscountValue = Long.parseLong(discountValue);
        if (longDiscountValue < ZERO || longDiscountValue > MAX_FIXED_AMOUNT) {
            throw new WrongRangeInputException();
        }
    }

    private static void validateDiscountPercent(String discountValue) {
        isNumeric(discountValue);
        Long longDiscountValue = Long.parseLong(discountValue);

        if (!(longDiscountValue > ZERO && longDiscountValue < MAX_PERCENT)) {
            throw new WrongRangeInputException();
        }
    }

    public static void validateVoucher(String type, String discountValue) {
        VoucherType voucherType = VoucherType.selectVoucherType(type);
        switch (voucherType) {
            case FIXED_AMOUNT -> validateDiscountAmount(discountValue);
            case PERCENTAGE -> validateDiscountPercent(discountValue);
            default -> throw new NotFindVoucherType();
        }
    }
}
