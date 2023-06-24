package org.prgrms.kdt.util;

import org.prgrms.kdt.exception.InvalidInputException;

import java.util.Arrays;

public enum Menu {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String menuString;

    Menu(String menuString) {
        this.menuString = menuString;
    }

    public static Menu getMenu(String str){
        return Arrays.stream(Menu.values())
                .filter((e) -> e.menuString.equals(str))
                .findFirst()
                .orElseThrow(InvalidInputException::new);
    }
}
