package com.prgrms.spring.domain.menu;

import com.prgrms.spring.exception.Error;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum MenuType {
    EXIT("exit", "exit the program"),
    CREATE_VOUCHER("createVoucher", "create a new voucher"),
    LIST_VOUCHER("listVoucher", "list all vouchers"),
    CREATE_CUSTOMER("createCustomer", "create a new customer"),
    LIST_CUSTOMER("listCustomer", "list all customers");

    private final String name;
    private final String explain;

    public static MenuType matchType(String type) {
        return Arrays.stream(values())
                .filter(v -> type.equals(v.name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.valueOf(Error.VALIDATION_WRONG_TYPE)));
    }
}
