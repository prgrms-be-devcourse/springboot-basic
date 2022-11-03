package com.programmers.commandLine.global.entity;

import java.util.Arrays;

public enum VoucherMenu {
    FIXEDAMOUNTVOUCHER(1),
    PERCENTDISCOUNTVOUCHER(2),
    ERROR(-1);

    private static final int BAD_REQUEST = -1;

    private int code;

    VoucherMenu(int code) {
        this.code = code;
    }

    public static VoucherMenu selectVoucherMenu(String input) {
        int code = toCode(input);
        return Arrays.stream(VoucherMenu.values())
                .filter(voucherMenu -> voucherMenu.code == code)
                .findFirst()
                .orElse(ERROR);
    }

    private static int toCode(String input) {
        try {
            return Integer.parseInt(input);
        } catch (RuntimeException e) {
            return BAD_REQUEST;
        }
    }
}
