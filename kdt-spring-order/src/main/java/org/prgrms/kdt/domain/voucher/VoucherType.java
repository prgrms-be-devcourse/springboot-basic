package org.prgrms.kdt.domain.voucher;

import java.util.Arrays;
import java.util.function.Predicate;

public enum VoucherType {
    FIX(0), PERCENT(1);

    private final int value;

    VoucherType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static VoucherType findType(int value) {
        return Arrays.stream(VoucherType.values())
                .filter(isSameValue(value))
                .findFirst()
                .get();

    }

    private static Predicate<VoucherType> isSameValue(int type) {
        return v -> v.getValue() == type;
    }
}
