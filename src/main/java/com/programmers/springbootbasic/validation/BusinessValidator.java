package com.programmers.springbootbasic.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusinessValidator {

    public static final String INVALID_CUSTOMER_ID_EXCEPTION_MESSAGE
            = "아이디의 길이는 1자 이상 12자 이하이고, 숫자와 영문자만 입력 가능하며 첫 글자는 영문자여야 합니다.";
    public static final String INVALID_CUSTOMER_NAME_EXCEPTION_MESSAGE
            = "이름은 영문자 또는 한글만 가능합니다.";
    public static final String NON_EXISTING_CUSTOMER_ID_EXCEPTION_MESSAGE
            = "존재하지 않는 고객 아이디입니다.";
    public static final String INVALID_INPUT_NUMBER_EXCEPTION_MESSAGE
            = "잘못된 숫자 형식입니다.";
    public static final String INVALID_VOUCHER_ID_EXCEPTION_MESSAGE
            = "적절한 할인권 아이디를 입력해 주세요.";
    public static final String NON_EXISTING_VOUCHER_ID_EXCEPTION_MESSAGE
            = "존재하지 않는 할인권 아이디입니다.";
    public static final String NON_EXISTING_CUSTOMER_HOLDING_THIS_VOUCHER_EXCEPTION_MESSAGE
            = "현재 이 할인권을 소유한 고객이 없습니다.";

    public static final long FIXED_AMOUNT_LIMIT = 1000000L;
    public static final int LENGTH_OF_UUID = 36;

    public static final String FIXED_AMOUNT_OUT_OF_BOUNDS_EXCEPTION_MESSAGE
            = "할인권의 액면가는 " + Long.toString(FIXED_AMOUNT_LIMIT) + "원 이하여야 합니다.";
    public static final String PERCENT_DISCOUNT_OUT_OF_BOUNDS_EXCEPTION_MESSAGE
            = "할인권의 할인율은 1 ~ 99 사이의 정수값이어야 합니다.";

    private static final String customerIdPattern = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$";
    private static final String customerNamePattern = "^[a-zA-Z가-힣]*$";

    public static boolean validateCustomerId(String customerId) {
        return isMatched(customerIdPattern, customerId);
    }

    public static boolean validateCustomerName(String name) {
        return isMatched(customerNamePattern, name);
    }

    private static boolean isMatched(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean validateVoucherId(String name) {
        return name.length() == LENGTH_OF_UUID;
    }

    public static boolean validateFixedAmountVoucher(long fixedAmount) {
        return 0 < fixedAmount && fixedAmount <= FIXED_AMOUNT_LIMIT;
    }

    public static boolean validatePercentDiscountVoucher(int percent) {
        return 0 < percent && percent < 99;
    }

}
