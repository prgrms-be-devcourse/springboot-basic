package com.programmers.springbasic.domain.voucher.model;

import lombok.Getter;

@Getter
public enum VoucherOption {
    FIXED_AMOUNT_VOUCHER("FIXED"), PERCENT_DISCOUNT_VOUCHER("PERCENT");

    private String voucherOption;

    VoucherOption(String voucherOption) {
        this.voucherOption = voucherOption;
    }
}
