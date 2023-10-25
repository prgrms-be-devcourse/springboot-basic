package com.prgrms.voucher_manage.console;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MenuType {
    CREATE_VOUCHER("createVoucher"),
    VOUCHER_LIST("voucherList"),

    FIND_VOUCHER("findVoucher"),

    UPDATE_VOUCHER("updateVoucher"),


    SAVE_CUSTOMER("saveCustomer"),

    ALL_CUSTOMERS("allCustomers"),

    BLACK_CUSTOMERS("blackCustomers"),

    NORMAL_CUSTOMERS("normalCustomers"),

    FIND_CUSTOMER("findCustomer"),

    UPDATE_CUSTOMER("updateCustomer"),

    EXIT("exit");

    private final String label;

    public static MenuType matchMenuType(String menu) {
        return Arrays.stream(MenuType.values())
                .filter(menuType -> menuType.getLabel().equals(menu))
                .findFirst()
                .orElse(null);
    }
}
