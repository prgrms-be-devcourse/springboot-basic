package com.devcourse.springbootbasic.application.dto;

import com.devcourse.springbootbasic.application.constant.ErrorMessage;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

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
                .filter(vt -> vt.typeOrdinal.equals(voucherTypeString) || Objects.equals(vt.typeString, voucherTypeString) || vt.name().equals(voucherTypeString))
                .findAny()
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_VOUCHER_TYPE.getMessageText()));
    }

    public String getTypeOrdinal() {
        return typeOrdinal;
    }

    public String getTypeString() {
        return typeString;
    }
}
