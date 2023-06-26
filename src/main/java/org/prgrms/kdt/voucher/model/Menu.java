package org.prgrms.kdt.voucher.model;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Menu {
    EXIT("exit", "Type exit to exit the program."),
    CREATE("create", "Type create to create a new voucher."),
    LIST("list","Type list to list all vouchers.");

    private final String menuName;
    private final String menuExplain;

    Menu(String menuName, String menuExplain) {
        this.menuName = menuName;
        this.menuExplain = menuExplain;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuExplain() {
        return menuExplain;
    }

    public static Stream<Menu> getMenusToStream() {
        return Arrays.stream(Menu.values());
    }
}
