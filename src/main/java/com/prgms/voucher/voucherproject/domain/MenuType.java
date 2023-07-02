package com.prgms.voucher.voucherproject.domain;

import java.util.InputMismatchException;

public enum MenuType {
    EXIT(1, "exit"),
    CREATE(2, "create"),
    LIST(3, "list");

    private int menuType;
    private String menuName;

    MenuType(int menuType, String menuName) {
        this.menuType = menuType;
        this.menuName = menuName;
    }

    public static MenuType getSelectedMenuType(String menuName) {
        return switch (menuName) {
            case "exit" -> EXIT;
            case "create" -> CREATE;
            case "list" -> LIST;
            default -> throw new InputMismatchException("잘못된 명령어입니다.");
        };
    }


}
