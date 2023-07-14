package com.devcourse.voucherapp.entity;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.exception.MenuInputException;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

public enum HomeMenu {
    VOUCHER("1", "할인권 메뉴"),
    CUSTOMER("2", "고객 메뉴"),
    QUIT("quit", "프로그램 종료");

    private static final Map<String, HomeMenu> MENUS = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(HomeMenu::getOption, Function.identity())));

    @Getter
    private final String option;

    private final String name;

    HomeMenu(String option, String name) {
        this.option = option;
        this.name = name;
    }

    public static HomeMenu from(String menuOption) {
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
