package com.programmers.springbasic.domain.voucher.view;

import lombok.Getter;

@Getter
public enum VoucherMenu {
    MAIN_MENU_MESSAGE("=== Voucher Program ==="),
    EXIT_MENU_MESSAGE("Type exit to exit the program."),
    CREATE_MENU_MESSAGE("Type create to create a new voucher."),
    LIST_MENU_MESSAGE("Type list to list all vouchers."),
    READ_MENU_MESSAGE("Type read to show voucher info."),
    UPDATE_MENU_MESSAGE("Type update to update voucher"),
    DELETE_MENU_MESSAGE("Type delete to remove voucher."),
    SHOW_MENU_MESSAGE("Type show to display the IDs of customers who have the corresponding voucher.");
    String message;

    VoucherMenu(String message) {
        this.message = message;
    }
}
