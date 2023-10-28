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

    DELETE_VOUCHER("deleteVoucher"),


    SAVE_CUSTOMER("saveCustomer"),

    ALL_CUSTOMERS("allCustomers"),

    BLACK_CUSTOMERS("blackCustomers"),

    FIND_CUSTOMER("findCustomer"),

    UPDATE_CUSTOMER("updateCustomer"),


    SAVE_WALLET("saveWallet"),

    FIND_WALLET1("findWallet1"),

    FIND_WALLET2("findWallet2"),

    DELETE_WALLET("deleteWallet"),

    EXIT("exit");

    private final String label;

    public static MenuType matchMenuType(String menu) {
        return Arrays.stream(MenuType.values())
                .filter(menuType -> menuType.getLabel().equals(menu))
                .findFirst()
                .orElse(null);
    }
}
