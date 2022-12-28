package org.programmers.weekly.mission.util.type;

import java.util.Optional;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED("FixedAmountVoucher"),
    PERCENT("PercentDiscountVoucher");

    private final String voucher;

    VoucherType(String voucher) {
        this.voucher = voucher;
    }

    public static VoucherType checkVoucherType(String input) {
        return Stream.of(VoucherType.values())
                .filter(voucherType -> voucherType.voucher.equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 입력값"));
    }
}
