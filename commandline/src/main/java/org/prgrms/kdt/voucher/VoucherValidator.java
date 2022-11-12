package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.IsNotNumberException;
import org.prgrms.kdt.exception.NotFindVoucherTypeException;
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
            throw new IsNotNumberException(ErrorCode.IS_NOT_NUMBER.getMessage());
        }
    }

    private static void validateDiscountAmount(String discountValue) {
        isNumeric(discountValue);
        Long longDiscountValue = Long.parseLong(discountValue);
        if (longDiscountValue < ZERO || longDiscountValue > MAX_FIXED_AMOUNT) {
            throw new WrongRangeInputException(ErrorCode.WRONG_RANGE_INPUT.getMessage());
        }
    }

    private static void validateDiscountPercent(String discountValue) {
        isNumeric(discountValue);
        Long longDiscountValue = Long.parseLong(discountValue);

        if (!(longDiscountValue > ZERO && longDiscountValue < MAX_PERCENT)) {
            throw new WrongRangeInputException(ErrorCode.WRONG_RANGE_INPUT.getMessage());
        }
    }

    public static void validateVoucher(String type, String discountValue) {
        VoucherType voucherType = VoucherType.selectVoucherType(type);
        switch (voucherType) {
            case FIXED_AMOUNT -> validateDiscountAmount(discountValue);
            case PERCENTAGE -> validateDiscountPercent(discountValue);
            default -> throw new NotFindVoucherTypeException(ErrorCode.NOT_FIND_VOUCHER_TYPE.getMessage());
        }
    }
}
