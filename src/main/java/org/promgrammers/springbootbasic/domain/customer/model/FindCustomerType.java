package org.promgrammers.springbootbasic.domain.customer.model;

import java.util.Arrays;

public enum FindCustomerType {

    ID("1"),
    USERNAME("2"),
    VOUCHER_ID("3"),
    BLACKLIST("4");

    private final String type;

    FindCustomerType(String type) {
        this.type = type;
    }

    public static FindCustomerType from(String inputType) {
        return Arrays.stream(values())
                .filter(findCustomer -> findCustomer.type.equals(inputType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 선택 입니다. => " + inputType));
    }
}
