package com.programmers.springbasic.domain.voucher.view;

import lombok.Getter;

@Getter
public enum VoucherCommandMessage {
    VOUCHER_OPTION_MESSAGE("input FIXED or PERCENT"),
    FIXED_AMOUNT_INPUT_MESSAGE("input fixed amount"),
    FIXED_AMOUNT_VOUCHER_CREATE_MESSAGE("Fixed Amount Voucher Created!!"),
    PERCENT_DISCOUNT_INPUT_MESSAGE("input percent discount"),
    PERCENT_DISCOUNT_VOUCHER_CREATE_MESSAGE("Percent Discount Voucher Created!!");

    String message;

    VoucherCommandMessage(String message) {
        this.message = message;
    }
}
