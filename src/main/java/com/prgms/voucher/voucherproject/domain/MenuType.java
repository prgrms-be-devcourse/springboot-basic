package com.prgms.voucher.voucherproject.domain;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;

public enum MenuType {

    EXIT,
    CREATE,
    LIST;

    public static MenuType getSelectedMenuType(String menuName) {
        return Arrays.stream(MenuType.values())
                .filter(menuType -> Objects.equals(menuType.name().toLowerCase(), menuName))
                .findAny()
                .orElseThrow(() -> new InputMismatchException("잘못된 메뉴 명령어입니다."));
    }

}
