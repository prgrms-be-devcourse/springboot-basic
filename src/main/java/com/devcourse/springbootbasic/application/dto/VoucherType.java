package com.devcourse.springbootbasic.application.dto;

import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.constant.Message;

import java.util.Arrays;
public enum VoucherType {
    FIXED_AMOUNT("1", "Fixed Amount Discount Voucher"),
    PERCENT_DISCOUNT("2", "Percent Discount Voucher");

    private final String typeOrdinal;
    private final String typeString;

    VoucherType(String typeOrdinal, String typeString) {
        this.typeOrdinal = typeOrdinal;
        this.typeString = typeString;
    }

    public static VoucherType getVoucherType(String voucherTypeString) {
        return Arrays.stream(VoucherType.values())
                .filter(vt -> vt.typeOrdinal.equals(voucherTypeString))
                .findAny()
                .orElseThrow(() -> new InvalidDataException(Message.INVALID_VOUCHER_TYPE));
    }

    public String getTypeOrdinal() {
        return typeOrdinal;
    }

    public String getTypeString() {
        return typeString;
    }
}
