package com.prgms.vouchermanager.contorller.customer;

import java.util.Arrays;

public enum CustomerMenuType {
    CREATE(1), UPDATE(2), FIND_ALL(3), FIND_ONE(4), DELETE_ONE(5), DELETE_ALL(6),BLACK_LIST(7);

    private final int number;

    CustomerMenuType(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public static CustomerMenuType fromValue(int menu) {

        return Arrays.stream(CustomerMenuType.values())
                .filter(customerMenuType -> customerMenuType.getNumber() == menu)
                .findFirst()
                .get();
    }
}
