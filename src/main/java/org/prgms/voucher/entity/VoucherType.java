package org.prgms.voucher.entity;

import java.util.Arrays;

import org.prgms.voucher.exception.WrongInputVoucherCommandException;

public enum VoucherType {
    FIXED_AMOUNT("1", "FIXED_AMOUNT"),
    PERCENT_DISCOUNT("2", "PERCENT_DISCOUNT");

    private final String command;
    private final String voucherName;

    VoucherType(String command, String voucherName) {
        this.command = command;
        this.voucherName = voucherName;
    }

    public static VoucherType findByCommand(String command) throws WrongInputVoucherCommandException {
        return Arrays.stream(VoucherType.values())
            .filter(type -> type.command.equals(command))
            .findFirst()
            .orElseThrow(WrongInputVoucherCommandException::new);
    }

    public String getVoucherName() {
        return voucherName;
    }
}
