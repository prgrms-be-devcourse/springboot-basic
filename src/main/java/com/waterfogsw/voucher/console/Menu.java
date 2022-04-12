package com.waterfogsw.voucher.console;

import com.waterfogsw.voucher.exception.InvalidMenuException;

public enum Menu {
    CREATE("create"),
    LIST("list"),
    BLACK_LIST("black_list"),
    EXIT("exit");

    private final String menu;

    Menu(String menu) {
        this.menu = menu;
    }



    public static Menu getMenu(String menuString) throws InvalidMenuException {
        return null;
    }
}
