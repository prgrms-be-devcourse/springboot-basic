package org.prgrms.voucherapp.global.enums;

// TODO : Regex 작성
public enum Regex {
    CUSTOMER_NAME("[a-zA-Z가-힣]+( [a-zA-Z가-힣]+)*"),
    CUSTOMER_EMAIL("\\w+@\\w+\\.\\w+(\\.\\w+)?");

    private final String regex;

    Regex(String regex) {
        this.regex = regex;
    }

    public String get() {
        return regex;
    }
}
