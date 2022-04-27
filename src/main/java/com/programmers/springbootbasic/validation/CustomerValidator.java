package com.programmers.springbootbasic.validation;

import static com.programmers.springbootbasic.validation.RegexMatcherUtils.isMatched;

public final class CustomerValidator {

    public static final String INVALID_CUSTOMER_ID_EXCEPTION_MESSAGE
            = "아이디의 길이는 1자 이상 12자 이하이고, 숫자와 영문자만 입력 가능하며 첫 글자는 영문자여야 합니다.";
    public static final String INVALID_CUSTOMER_NAME_EXCEPTION_MESSAGE
            = "이름은 영문자 또는 한글만 가능합니다.";
    public static final String DUPLICATE_CUSTOMER_ID_EXCEPTION_MESSAGE
            = "이미 존재하는 고객 아이디입니다. 다른 아이디를 입력해 주세요.";
    public static final String NON_EXISTING_CUSTOMER_ID_EXCEPTION_MESSAGE
            = "존재하지 않는 고객 아이디입니다.";
    public static final String NON_EXISTING_CUSTOMER_HOLDING_THIS_VOUCHER_EXCEPTION_MESSAGE
            = "현재 이 할인권을 소유한 고객이 없습니다.";

    private static final String customerIdPattern = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$";
    private static final String customerNamePattern = "^[a-zA-Z가-힣]*$";

    private CustomerValidator() {}

    public static boolean isValidCustomerId(String customerId) {
        return isMatched(customerIdPattern, customerId);
    }

    public static boolean isValidCustomerName(String name) {
        return isMatched(customerNamePattern, name);
    }

}
