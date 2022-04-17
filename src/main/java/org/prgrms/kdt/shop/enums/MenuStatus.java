package org.prgrms.kdt.shop.enums;

import java.util.Arrays;

public enum MenuStatus {
    LIST("list"), EXIT("exit"), CREATE("create");

    private final String inputMenu;

    MenuStatus(String inputMenu) {
        this.inputMenu = inputMenu;
    }

    public static MenuStatus find(String inputMenu) {
        return Arrays.stream(values()).filter(menuStatus -> menuStatus.inputMenu.equals(inputMenu)).findAny().orElseThrow(( ) -> new IllegalArgumentException("\n[ERROR] 제대로 된 문자를 입력하세요."));
    }
}
