package org.prgrms.vouchermanagement.console.io;

import java.util.Arrays;
import java.util.Objects;

public enum Menu {
    EXIT_PROGRAM("exit", "Type exit to exit the program."),
    CREATE_VOUCHER("create", "Type create to create a new voucher."),
    LIST_VOUCHERS("list", "Type list to list all vouchers."),
    LIST_BLACK_CUSTOMERS("black", "Type black to list all black customers."),
    INVALID_MENU_INPUT("invalidMenuInput", "Invalid input! Please type a valid input."),
    INSERT_VOUCHER_TYPE("insertVoucherType", "Insert voucher type: "),
    INSERT_DISCOUNT_VALUE("insertDiscountValue", "Insert fixed amount or discount percent: ");

    private String inputMenu;
    private String message;

    Menu(String inputMenu, String message) {
        this.inputMenu = inputMenu;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static Menu getMenu(String inputMenu) {
        return Arrays.stream(values())
                .filter(o -> Objects.equals(o.inputMenu, inputMenu))
                .findFirst().get();
    }
}