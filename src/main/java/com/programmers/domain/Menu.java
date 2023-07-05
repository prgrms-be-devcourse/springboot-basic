package com.programmers.domain;

import com.programmers.exception.EmptyException;
import com.programmers.exception.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

public enum Menu {
    EXIT("exit", "1"),
    CREATE("create", "2"),
    LIST("list", "3"),
    UPDATE("update", "4"),
    DELETE("delete", "5");

    private static final Logger log = LoggerFactory.getLogger(Menu.class);

    private final String name;
    private final String number;

    Menu(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public static Menu findMenu(String input) {
        checkMenuInputEmpty(input);

        return Arrays.stream(Menu.values())
                .filter(menu -> Objects.equals(menu.name, input) || Objects.equals(menu.number, input))
                .findAny()
                .orElseThrow(() -> {
                    log.error("The invalid menu input found. input value = {}", input);
                    return new InvalidInputException("[ERROR] 입력하신 메뉴 값이 유효하지 않습니다.");
                });
    }

    private static void checkMenuInputEmpty(String input) {
        if (input.isEmpty()) {
            log.error("The menu input not found.");
            throw new EmptyException("[ERROR] 메뉴 값이 입력되지 않았습니다.");
        }
    }
}
