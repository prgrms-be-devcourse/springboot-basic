package com.programmers.domain;

import java.util.Arrays;
import java.util.Objects;

public enum Menu {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String name;

    Menu(String name) {
        this.name = name;
    }

    public static Menu findMenu(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return Arrays.stream(Menu.values())
                .filter(menu -> Objects.equals(menu.name, input))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
