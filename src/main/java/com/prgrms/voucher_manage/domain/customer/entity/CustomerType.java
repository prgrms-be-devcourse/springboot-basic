package com.prgrms.voucher_manage.domain.customer.entity;

import com.prgrms.voucher_manage.console.MenuType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor @Getter
public enum CustomerType {
    BLACK("B"),
    NORMAL("N");
    private final String label;

    public static CustomerType matchCustomerType(String menu) {
        return Arrays.stream(CustomerType.values())
                .filter(customerType -> customerType.getLabel().equals(menu))
                .findFirst()
                .orElse(null);
    }
}
