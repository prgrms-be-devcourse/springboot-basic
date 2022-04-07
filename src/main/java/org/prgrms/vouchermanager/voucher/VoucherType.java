package org.prgrms.vouchermanager.voucher;

import java.util.Optional;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED, PERCENT;

    public static Optional<VoucherType> findVoucherType(String type) {
        return Stream.of(values())
                .filter(v -> type.equalsIgnoreCase(v.toString()))
                .findFirst();
    }

}
