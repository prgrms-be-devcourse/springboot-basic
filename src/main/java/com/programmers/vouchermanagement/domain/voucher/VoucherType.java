package com.programmers.vouchermanagement.domain.voucher;

public enum VoucherType {
    FIXED, PERCENTAGE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
