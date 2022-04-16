package org.prgms.voucherProgram.entity.voucher;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT("1"),
    PERCENT_DISCOUNT("2");

    private static final String ERROR_WRONG_VOUCHER_COMMAND_MESSAGE = "[ERROR] 올바른 바우처 커맨드가 아닙니다.";

    private final String command;

    VoucherType(String command) {
        this.command = command;
    }

    public static VoucherType findByCommand(String command) {
        return Arrays.stream(VoucherType.values())
            .filter(type -> type.command.equals(command))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(ERROR_WRONG_VOUCHER_COMMAND_MESSAGE));
    }
}
