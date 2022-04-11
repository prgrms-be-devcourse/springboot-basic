package org.prgrms.kdtspringdemo.console;

public enum Menu {
    EXIT("exit", 1),
    CREATE("create", 2),
    LIST("list", 3),
    ERROR("error", 0);

    private final String menuName;
    private final int checkNumber;

    Menu(String menuName, int checkNumber) {
        this.menuName = menuName;
        this.checkNumber = checkNumber;
    }

    public String getMenuName() {

        return menuName;
    }
}
