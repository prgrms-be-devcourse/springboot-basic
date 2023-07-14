package org.prgrms.assignment.voucher.model;

import org.prgrms.kdt.voucher.model.Command;

public enum Menu {

    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String menuName;

    Menu(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuName() {
        return menuName;
    }

    public static Menu of(Command command) {
        return Menu.valueOf(command.getCommand());
    }

}
