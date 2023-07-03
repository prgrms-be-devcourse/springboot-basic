package org.promgrammers.springbootbasic.domain.customer.model;

import java.util.Arrays;

public enum DeleteCustomerType {

    ID("1"),
    ALL("2");

    private final String type;

    DeleteCustomerType(String type) {
        this.type = type;
    }

    public static DeleteCustomerType from(String inputType) {
        return Arrays.stream(values())
                .filter(deleteCustomer -> deleteCustomer.type.equals(inputType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 선택 입니다. => " + inputType));
    }
}
