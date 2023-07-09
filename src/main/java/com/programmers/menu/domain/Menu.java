package com.programmers.menu.domain;

import com.programmers.exception.InvalidRequestValueException;
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

    public static Menu findMenu(String menuRequest) {
        checkMenuRequestEmpty(menuRequest);

        return Arrays.stream(Menu.values())
                .filter(menu -> Objects.equals(menu.name, menuRequest) || Objects.equals(menu.number, menuRequest))
                .findAny()
                .orElseThrow(() -> {
                    log.error("The invalid menu request found. request value = {}", menuRequest);
                    return new InvalidRequestValueException("[ERROR] 요청하신 메뉴 값이 유효하지 않습니다.");
                });
    }

    private static void checkMenuRequestEmpty(String menuRequest) {
        if (menuRequest.isEmpty()) {
            log.error("The menu request not found.");
            throw new InvalidRequestValueException("[ERROR] 메뉴 요청 값이 비었습니다.");
        }
    }
}
