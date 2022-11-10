package org.programmers.kdtspringdemo.type;

import java.util.Optional;

public enum VoucherType {
    FIXED("FixedAmountVoucher"),
    PERCENT("PercentDiscountVoucher");

    private final String voucher;

    VoucherType(String voucher) {
        this.voucher = voucher;
    }

    public static Optional<VoucherType> checkVoucherType(String input) {
        for (VoucherType voucherType : VoucherType.values()) {
            if (voucherType.toString().equalsIgnoreCase(input)) return Optional.of(voucherType);
        }
        return Optional.empty();
    }
}
