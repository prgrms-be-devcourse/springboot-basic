package org.prgms.voucherProgram.entity.voucher;

import java.util.Arrays;

import org.prgms.voucherProgram.exception.WrongInputVoucherCommandException;

public enum VoucherType {
    FIXED_AMOUNT("1"),
    PERCENT_DISCOUNT("2");

    private final String command;

    VoucherType(String command) {
        this.command = command;
    }

    public static VoucherType findByCommand(String command) throws WrongInputVoucherCommandException {
        return Arrays.stream(VoucherType.values())
            .filter(type -> type.command.equals(command))
            .findFirst()
            .orElseThrow(WrongInputVoucherCommandException::new);
    }
}
