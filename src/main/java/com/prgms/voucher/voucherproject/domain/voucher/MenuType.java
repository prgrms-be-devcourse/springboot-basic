package com.prgms.voucher.voucherproject.domain.voucher;

import java.util.Arrays;
import java.util.InputMismatchException;

public enum MenuType {

    EXIT,
    CREATE,
    LIST;

    public static MenuType getSelectedMenuType(String menuName) {
        return Arrays.stream(MenuType.values())
                .filter(menuType -> menuType.name().equalsIgnoreCase(menuName))
                .findAny()
                .orElseThrow(() -> new InputMismatchException("잘못된 메뉴 명령어입니다."));
    }

}
