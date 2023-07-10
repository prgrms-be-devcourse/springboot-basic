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
    READ("2", "전체 할인권 조회"),
    UPDATE("3", "할인권 수정"),
    DELETE("4", "할인권 삭제"),
    CUSTOMER_CREATE("5", "새 고객 생성"),
    CUSTOMER_READ("6", "전체 고객 조회"),
    CUSTOMER_UPDATE("7", "고객 수정"),
    QUIT("quit", "프로그램 종료");

    private static final Map<String, Menu> MENUS = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(Menu::getOption, Function.identity())));

    @Getter
    private final String option;

    private final String name;

    Menu(String option, String name) {
        this.option = option;
        this.name = name;
    }

    public static Menu from(String menuOption) {
        if (MENUS.containsKey(menuOption)) {
            return MENUS.get(menuOption);
        }

        throw new MenuInputException(menuOption);
    }

    @Override
    public String toString() {
        return format("{0}. {1}", option, name);
    }
}
