package org.prgrms.kdt.util;

import org.prgrms.kdt.exception.InvalidInputException;

import java.util.Arrays;
import java.util.Objects;

public enum Menu {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACK_LIST("blacklist");

    private final String descripton;

    Menu(String descripton) {
        this.descripton = descripton;
    }

    public static Menu getMenu(String str) {
        return Arrays.stream(Menu.values())
                .filter((e) -> Objects.equals(e.descripton, str))
                .findFirst()
                .orElseThrow(InvalidInputException::new);
    }

    public boolean isNotExit(){
        return this != EXIT;
    }
}
