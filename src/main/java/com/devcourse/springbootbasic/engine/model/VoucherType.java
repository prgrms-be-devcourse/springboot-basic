package com.devcourse.springbootbasic.engine.model;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;
import com.devcourse.springbootbasic.engine.config.Message;

import java.util.Arrays;
public enum VoucherType {
    FIXED_AMOUNT("1", "Amount"),
    PERCENT_DISCOUNT("2", "Percent");

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

    public String getTypeString() {
        return typeString;
    }
}
