package org.prgrms.kdt.menu;

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
}
