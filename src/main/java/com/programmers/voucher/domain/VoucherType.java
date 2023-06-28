package com.programmers.voucher.domain;

import java.util.Arrays;

public enum VoucherType {
    FIXED,
    PERCENT;

    private static final String WRONG_COMMAND_MESSAGE = "[ERROR] 잘못된 커맨드입니다. 올바른 커맨드를 입력해 주세요. ";
    private static final int FOR_INDEX = 1;

    public static VoucherType of(String command) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.ordinal() == Integer.parseInt(command) - FOR_INDEX)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(WRONG_COMMAND_MESSAGE));
    }
}
