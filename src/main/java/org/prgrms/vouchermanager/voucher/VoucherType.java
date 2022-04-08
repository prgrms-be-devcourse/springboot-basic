package org.prgrms.vouchermanager.voucher;

import java.util.stream.Stream;

public enum VoucherType {
    INVALID,
    FIXED,
    PERCENT;

    public static VoucherType findVoucherType(String type) {
        return Stream.of(values())
                .filter(v -> type.equalsIgnoreCase(v.toString()))
                .findFirst().orElse(INVALID);
    }

}
