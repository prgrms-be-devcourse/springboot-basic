package com.devcourse.voucherapp.entity;

import com.devcourse.voucherapp.exception.MenuInputException;
import java.util.Arrays;

public enum Menu {
    CREATE("1", "새 할인권 생성"),
    LIST("2", "할인권 조회"),
    QUIT("3", "프로그램 종료");

    private static final String NOT_EXIST_MENU_MESSAGE = "입력하신 메뉴는 없는 메뉴입니다.";

    private final String number;
    private final String name;

    Menu(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public static Menu getMenu(String menuNumber) {
        return Arrays.stream(Menu.values())
            .filter(menu -> menuNumber.equals(menu.getNumber()))
            .findFirst()
            .orElseThrow(() -> new MenuInputException(NOT_EXIST_MENU_MESSAGE));
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number + ". " + name;
    }
}
