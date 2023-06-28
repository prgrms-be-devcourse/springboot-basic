package org.programers.vouchermanagement.voucher.domain;

public enum VoucherType {
    FIXED_AMOUNT, PERCENT;

    boolean isFixedAmount() {
        return this == FIXED_AMOUNT;
    }

    boolean isPercent() {
        return this == PERCENT;
    }
}
