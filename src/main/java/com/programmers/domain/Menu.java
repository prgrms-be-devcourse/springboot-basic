package com.programmers.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

public enum Menu {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACKLIST("black");

    private static final Logger log = LoggerFactory.getLogger(Menu.class);

    private final String name;

    Menu(String name) {
        this.name = name;
    }

    public static Menu findMenu(String input) {
        log.warn("The menu input will be validated.");

        if (input.isEmpty()) {
            log.error("The menu input not found.");
            throw new IllegalArgumentException();
        }

        return Arrays.stream(Menu.values())
                .filter(menu -> Objects.equals(menu.name, input))
                .findAny()
                .orElseThrow(() -> {
                    log.error("The invalid menu input found. input value = {}", input);
                    return new IllegalArgumentException();
                });
    }
}
