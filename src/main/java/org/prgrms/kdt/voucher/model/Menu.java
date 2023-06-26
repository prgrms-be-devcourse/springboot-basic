package org.prgrms.kdt.voucher.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum Menu {
    EXIT("exit", "Type exit to exit the program."),
    CREATE("create", "Type create to create a new voucher."),
    LIST("list","Type list to list all vouchers.");

    private static final Map<String, Menu> commandToMenuMap = new HashMap<>();
    static {
        for(Menu menu : Menu.values()) {
            commandToMenuMap.put(menu.menuName, menu);
        }
    }
    private final String menuName;
    private final String menuExplain;
    private static final Menu[] menus = Menu.values();

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
    public static boolean isValidMenu(String command) {
        for (Menu menu : menus) {
            if (menu.getMenuName().equals(command)) {
                return true;
            }
        }
        return false;
    }

    public static Menu of(String voucherType) {
        return commandToMenuMap.get(voucherType);
    }
}
