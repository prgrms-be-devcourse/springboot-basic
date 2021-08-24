package org.prgrms.kdt.voucher.model;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER ("FixedAmountVoucher"),
    PERCENT_DISCOUNT_VOUCHER ("PercentDiscountVoucher"),
    UNKNOWN("알수없음");

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static VoucherType getVoucherType(String type) {
        return Arrays.stream(values())
                .filter(voucherType -> voucherType.type.equals(type))
                .findAny()
                .orElse(UNKNOWN);
    }
}
