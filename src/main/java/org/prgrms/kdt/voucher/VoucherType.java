package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exception.InvalidArgumentException;

import java.util.Arrays;

import static org.prgrms.kdt.exception.InvalidArgumentException.*;

public enum VoucherType {
    FIXED("Fixed"),
    PERCENT("Percent");

    private final String type;

    VoucherType(final String type) {
        this.type = type;
    }

    public static VoucherType findByVoucherType(String inputType) {
        return Arrays.stream(VoucherType.values())
                .filter(b -> b.type.equals(inputType))
                .findAny()
                .orElseThrow(() -> new InvalidArgumentException(ErrorMessage.NOT_CORRECT_INPUT_MESSAGE));
    }

}
