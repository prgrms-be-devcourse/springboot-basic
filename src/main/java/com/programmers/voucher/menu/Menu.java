package com.programmers.voucher.menu;

public enum Menu {
    EXIT("EXIT"),
    CREATE("CREATE"),
    LIST("LIST"),
    ;

    private final String menu;

    Menu(String menu) {
        this.menu = menu;
    }

    public String getMenu() {
        return menu;
    }
}
