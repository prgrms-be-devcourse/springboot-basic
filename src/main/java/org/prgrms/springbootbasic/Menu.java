package org.prgrms.springbootbasic;

import java.util.Arrays;

public enum Menu {
    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private final String menuItem;

    Menu(String input) {
        this.menuItem = input;
    }

    public String getMenuItem() {
        return menuItem;
    }

    public static boolean isExist(String input) {
        return EXIT.menuItem.equals(input);
    }

    public static boolean isCreateVoucher(String input) {
        return CREATE.menuItem.equals(input);
    }

    public static boolean validate(String input) {
        return Arrays.stream(Menu.values())
                .map(Menu::getMenuItem)
                .anyMatch(choice -> choice.equals(input));
    }
}
