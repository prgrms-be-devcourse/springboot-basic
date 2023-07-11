package com.dev.voucherproject.model.menu;

import java.text.MessageFormat;
import java.util.Arrays;

public enum VoucherMenu {
    CREATE("1"), LIST("2"), FIND_BY_ID("3"), FIND_BY_POLICY("4"),
    DELETE_ALL("5"), DELETE_BY_ID("6"), UPDATE("7"), MAIN("8");

    private final String voucherMenuName;

    VoucherMenu(String voucherMenuName) {
        this.voucherMenuName = voucherMenuName;
    }

    public static VoucherMenu convertInputToMenu(final String input) {
        return Arrays.stream(VoucherMenu.values())
            .filter(voucherMenuName -> voucherMenuName.isExistVoucherMenu(input))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("{0} 입력에 해당하는 메뉴를 찾을 수 없습니다.", input)));
    }

    private boolean isExistVoucherMenu(final String input) {
        return this.voucherMenuName.equals(input);
    }

    public String getVoucherMenuName() {
        return voucherMenuName;
    }
}
