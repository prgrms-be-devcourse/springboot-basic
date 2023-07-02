package com.programmers.springbasic.domain.voucher.view;

import lombok.Getter;

@Getter
public enum VoucherMenu {
    MAIN_MENU_MESSAGE("=== Voucher Program ==="),
    EXIT_MENU_MESSAGE("Type exit to exit the program."),
    CREATE_MENU_MESSAGE("Type create to create a new voucher."),
    LIST_MENU_MESSAGE("Type list to list all vouchers.");

    String message;

    VoucherMenu(String message) {
        this.message = message;
    }
}
