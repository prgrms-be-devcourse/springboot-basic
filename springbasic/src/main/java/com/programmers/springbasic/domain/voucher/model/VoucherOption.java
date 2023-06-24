package com.programmers.springbasic.domain.voucher.model;

public enum VoucherOption {
    FIXED_AMOUNT_VOUCHER("F"), PERCENT_DISCOUNT_VOUCHER("P");

    private String voucherOption;

    VoucherOption(String voucherOption) {
        this.voucherOption = voucherOption;
    }

    @Override
    public String toString() {
        return voucherOption;
    }
}
