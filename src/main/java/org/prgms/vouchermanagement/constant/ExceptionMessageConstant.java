package org.prgms.vouchermanagement.constant;

public final class ExceptionMessageConstant {
    private ExceptionMessageConstant() {}

    public static final String COMMAND_INPUT_EXCEPTION = "[ERROR] 지원되지 않는 Command 입니다.";
    public static final String VOUCHER_TYPE_INPUT_EXCEPTION = "[ERROR] 1 또는 2를 입력해주세요.";
    public static final String FIXED_VOUCHER_AMOUNT_INPUT_EXCEPTION = "[ERROR] 올바른 Amount 값이 아닙니다.";
    public static final String PERCENT_DISCOUNT_INPUT_EXCEPTION = "[ERROR] 올바른 Percent 값이 아닙니다.";
}
