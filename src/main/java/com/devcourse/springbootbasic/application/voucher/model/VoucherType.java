package com.devcourse.springbootbasic.application.voucher.model;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;

import java.util.Arrays;
import java.util.Objects;

public enum VoucherType {
    FIXED_AMOUNT("Fixed Amount Discount Voucher"),
    PERCENT_DISCOUNT("Percent Discount Voucher");

    private final String typePrompt;

    VoucherType(String typeString) {
        this.typePrompt = typeString;
    }

    public static VoucherType getVoucherType(String voucherTypeString) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> Objects.equals(voucherType.name(), voucherTypeString) || Objects.equals(String.valueOf(voucherType.ordinal()), voucherTypeString))
                .findAny()
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_MENU.getMessageText()));
    }

    public String getTypeDescription() {
        return typePrompt;
    }

}
