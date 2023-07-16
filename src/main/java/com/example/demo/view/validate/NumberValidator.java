package com.example.demo.view.validate;

import com.example.demo.util.VoucherDiscountType;
import java.util.regex.Pattern;

public final class NumberValidator {

    private static final Pattern IS_NUMERIC_REGEX = Pattern.compile("^[1-9]\\d*$");
    private static final Pattern IS_PERCENT_REGEX = Pattern.compile("^([1-9]|[1-9][0-9]|100)$");


    private NumberValidator() {
    }

    public static void validateAmount(VoucherDiscountType voucherDiscountType, String inputAmount) {
        switch (voucherDiscountType) {
            case FIX -> NumberValidator.validatePositiveNumber(inputAmount);
            case PERCENT -> {
                NumberValidator.validatePositiveNumber(inputAmount);
                NumberValidator.validatePercentageNumber(inputAmount);
            }
            default -> throw new IllegalArgumentException(String.format("입력하신 %s는 유효한 바우처 타입이 아닙니다.", voucherDiscountType.name()));
        }
    }

    public static void validateAmount(VoucherDiscountType voucherDiscountType, double inputAmount) {
        switch (voucherDiscountType) {
            case FIX -> NumberValidator.validatePositiveNumber(inputAmount);
            case PERCENT -> {
                NumberValidator.validatePositiveNumber(inputAmount);
                NumberValidator.validatePercentageNumber(inputAmount);
            }
            default -> throw new IllegalArgumentException(String.format("입력하신 %s는 유효한 바우처 타입이 아닙니다.", voucherDiscountType.name()));
        }
    }

    private static void validatePositiveNumber(String inputAmount) {
        if (!IS_NUMERIC_REGEX.matcher(inputAmount).matches()) {
            throw new IllegalArgumentException("[ERROR] 1이상의 숫자를 입력해주세요.");
        }
    }

    private static void validatePositiveNumber(double inputAmount) {
        if (inputAmount < 1) {
            throw new IllegalArgumentException("[ERROR] 1이상의 숫자를 입력해주세요.");
        }
    }

    private static void validatePercentageNumber(String inputAmount) {
        if (!IS_PERCENT_REGEX.matcher(inputAmount).matches()) {
            throw new IllegalArgumentException("[ERROR] 1이상 ~ 100 이하의 숫자를 입력해주세요.");
        }
    }

    private static void validatePercentageNumber(double inputAmount) {
        if (inputAmount < 1 || inputAmount > 100) {
            throw new IllegalArgumentException("[ERROR] 1이상 ~ 100 이하의 숫자를 입력해주세요.");
        }
    }
}
