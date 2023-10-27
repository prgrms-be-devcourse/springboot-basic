package com.prgrms.voucher_manage.domain.customer.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor @Getter
public enum CustomerType {
    BLACK("black","B"),
    NORMAL("normal","N");
    private final String label;
    private final String data;

    public static CustomerType matchTypeByString(String type) {
        return Arrays.stream(CustomerType.values())
                .filter(customerType -> customerType.getLabel().equals(type) || customerType.getData().equals(type))
                .findFirst()
                .orElse(null);
    }
}
