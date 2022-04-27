package com.programmers.springbootbasic.validation;

import static com.programmers.springbootbasic.validation.RegexMatcherUtils.*;

public final class VoucherValidator {

    public static final long FIXED_AMOUNT_LIMIT = 1000000L;
    public static final int LENGTH_OF_UUID = 36;

    private static final String voucherIdPattern =
            "^[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}$";

    public static final String FIXED_AMOUNT_OUT_OF_BOUNDS_EXCEPTION_MESSAGE
            = "할인권의 액면가는 " + Long.toString(FIXED_AMOUNT_LIMIT) + "원 이하여야 합니다.";
    public static final String PERCENT_DISCOUNT_OUT_OF_BOUNDS_EXCEPTION_MESSAGE
            = "할인권의 할인율은 1 ~ 100 사이의 정수값이어야 합니다.";
    public static final String INVALID_INPUT_NUMBER_EXCEPTION_MESSAGE
            = "잘못된 숫자 형식입니다.";
    public static final String INVALID_VOUCHER_ID_EXCEPTION_MESSAGE
            = "적절한 할인권 아이디를 입력해 주세요.";
    public static final String NON_EXISTING_VOUCHER_ID_EXCEPTION_MESSAGE
            = "존재하지 않는 할인권 아이디입니다.";
    public static final String DUPLICATE_VOUCHER_ID_EXCEPTION_MESSAGE
            = "이미 할당된 할인권입니다. 다른 할인권 아이디를 입력해 주세요.";

    private VoucherValidator() {}

    public static boolean isValidVoucherId(String name) {
        return name.length() == LENGTH_OF_UUID && isMatched(voucherIdPattern, name);
    }

    public static boolean isValidFixedAmountVoucher(long fixedAmount) {
        return 0 < fixedAmount && fixedAmount <= FIXED_AMOUNT_LIMIT;
    }

    public static boolean isValidPercentDiscountVoucher(int percent) {
        return 0 < percent && percent <= 100;
    }

}
