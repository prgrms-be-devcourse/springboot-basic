package com.prgrms.voucher_manage.domain.customer.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor @Getter
public enum CustomerType {
    BLACK("B"),
    NORMAL("N");
    private final String label;

    public static CustomerType matchCustomerType(String type) {
        return Arrays.stream(CustomerType.values())
                .filter(customerType -> customerType.getLabel().equals(type))
                .findFirst()
                .orElse(null);
    }
}
