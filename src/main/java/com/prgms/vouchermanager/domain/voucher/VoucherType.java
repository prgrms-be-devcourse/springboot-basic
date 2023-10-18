package com.prgms.vouchermanager.domain.voucher;

public enum VoucherType {
    FIXED_AMOUNT(1), PERCENT_DISCOUNT(2);

    private final int number;

    VoucherType(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }


}
