package org.prgrms.kdt.voucher;

import java.util.Arrays;

public enum VoucherType {
    FIXED("FixedAmountVoucher"),
    PERCENT("PercentDiscountVoucher");

    private final String voucherType;

    VoucherType(final String voucherType) {
        this.voucherType = voucherType;
    }

    public static VoucherType of(final String type) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.isVoucherType(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 타입을 입력받았습니다."));
    }

    private boolean isVoucherType(final String type) {
        return this.voucherType.equals(type);
    }
}
