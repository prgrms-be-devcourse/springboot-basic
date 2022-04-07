package org.voucherProject.voucherProject.entity.voucher;

public enum VoucherType {
    FIXED(1), PERCENT(2);

    private final int number;

    VoucherType(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
