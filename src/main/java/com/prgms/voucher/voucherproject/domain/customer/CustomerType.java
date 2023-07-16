package com.prgms.voucher.voucherproject.domain.customer;

import java.util.InputMismatchException;
import java.util.stream.Stream;

public enum CustomerType {
    CREATE,
    LIST,
    FIND,
    DELETE,
    EXIT
    ;

    public static CustomerType getSelectedCustomerType(String selectedCustomer) {
        return Stream.of(CustomerType.values())
                .filter(customerType -> customerType.name().equalsIgnoreCase(selectedCustomer))
                .findAny()
                .orElseThrow(() -> new InputMismatchException("잘못된 사용자 명령어입니다."));
    }

}
