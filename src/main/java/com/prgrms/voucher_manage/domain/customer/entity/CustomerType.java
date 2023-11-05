package com.prgrms.voucher_manage.domain.customer.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum CustomerType {
    BLACK( "B"),
    NORMAL( "N");
    
    private final String data;

    public static CustomerType matchTypeByString(String type) {
        return Arrays.stream(CustomerType.values())
                .filter(customerType -> customerType.toString().toLowerCase().equals(type) || customerType.getData().equals(type))
                .findFirst()
                .orElse(null);
    }
}
