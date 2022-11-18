package org.programmers.weekly.mission.util.type;

import java.util.Optional;

public enum VoucherType {
    FIXED("FixedAmountVoucher"),
    PERCENT("PercentDiscountVoucher");

    private final String voucher;

    VoucherType(String voucher) {
        this.voucher = voucher;
    }

    public static Optional<VoucherType> checkVoucherType(String input) {
        // stream 사용
        for (VoucherType voucherType : VoucherType.values()) {
            if (voucherType.voucher.equalsIgnoreCase(input)) return Optional.of(voucherType);
        }
        return Optional.empty();
    }
}
