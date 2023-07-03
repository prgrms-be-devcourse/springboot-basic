package com.programmers.springbasic.domain.voucher.view;

import lombok.Getter;

@Getter
public enum VoucherInfo {
    VOUCHER_ID_INFO_MESSAGE("voucherID: "),
    VOUCHER_VALUE_INFO_MESSAGE("value: "),
    VOUCHER_EXPIRE_INFO_MESSAGE("expireDate: "),
    VOUCHER_VALID_INFO_MESSAGE("valid: "),
    VOUCHER_TYPE_INFO_MESSAGE("voucher type: "),
    VOUCHER_CUSTOMER_INFO_MESSAGE("customer ID: ");

    String infoMessage;

    VoucherInfo(String infoMessage) {
        this.infoMessage = infoMessage;
    }
}
