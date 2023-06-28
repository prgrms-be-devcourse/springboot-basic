package com.devcourse.voucherapp.entity;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.exception.MenuInputException;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

public enum Menu {
    CREATE("1", "새 할인권 생성"),
    LIST("2", "할인권 조회"),
    QUIT("3", "프로그램 종료");

    private static final String NOT_EXIST_MENU_MESSAGE = "입력하신 메뉴는 없는 메뉴입니다.";
    private static final Map<String, Menu> MENUS = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(Menu::getNumber, Function.identity())));

    @Getter
    private final String number;

    private final String name;

    Menu(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public static Menu of(String menuNumber) {
        if (MENUS.containsKey(menuNumber)) {
            return MENUS.get(menuNumber);
        }

        throw new MenuInputException(NOT_EXIST_MENU_MESSAGE);
    }

    @Override
    public String toString() {
        return format("{0}. {1}", number, name);
    }
}
