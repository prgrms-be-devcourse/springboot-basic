package com.devcourse.springbootbasic.application.voucher.model;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;

import java.util.Arrays;
import java.util.Objects;

public enum VoucherType {
    FIXED_AMOUNT,
    PERCENT_DISCOUNT;

    public static VoucherType getVoucherType(String voucherTypeString) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> Objects.equals(voucherType.name(), voucherTypeString) || Objects.equals(String.valueOf(voucherType.ordinal()), voucherTypeString))
                .findAny()
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_MENU.getMessageText()));
    }

}
