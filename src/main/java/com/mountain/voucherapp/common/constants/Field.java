package com.mountain.voucherapp.common.constants;

public enum Field {
    CUSTOMER_ID("customer_id"),
    CUSTOMER_ID_CAMEL("customerId"),
    NAME("name"),
    EMAIL("email"),
    LAST_LOGIN_AT("last_login_at"),
    LAST_LOGIN_AT_CAMEL("lastLoginAt"),
    CREATED_AT("created_at"),
    CREATED_AT_CAMEL("createdAt"),
    VOUCHER_ID("voucher_id"),
    VOUCHER_ID_CAMEL("voucherId"),
    DISCOUNT_AMOUNT("discount_amount"),
    DISCOUNT_AMOUNT_CAMEL("discountAmount"),
    DISCOUNT_POLICY("discount_policy"),
    DISCOUNT_POLICY_CAMEL("discountPolicy");

    private final String value;

    Field(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
