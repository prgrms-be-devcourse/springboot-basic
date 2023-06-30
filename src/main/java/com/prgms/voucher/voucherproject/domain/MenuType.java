package com.prgms.voucher.voucherproject.domain;

import java.util.InputMismatchException;

public enum MenuType {
    EXIT(1, "exit"), CREATE(2, "create"), LIST(3, "list");

    private int menuType;
    private String menuName;

    MenuType(int i, String menuName) {
        this.menuType = i;
        this.menuName = menuName;
    }

    public static MenuType getSelectedMenuType(String menuName) {
        switch (menuName) {
            case "exit":
                return EXIT;
            case "create":
                return CREATE;
            case "list":
                return LIST;
            default:
                throw new InputMismatchException("잘못된 명령어입니다.");
        }
    }


}
