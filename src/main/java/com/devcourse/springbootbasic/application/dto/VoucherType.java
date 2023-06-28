package com.devcourse.springbootbasic.application.dto;

import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.constant.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
public enum VoucherType {
    FIXED_AMOUNT("1", "Fixed Amount Discount Voucher"),
    PERCENT_DISCOUNT("2", "Percent Discount Voucher");

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);

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
                .orElseThrow(() -> {
                    logger.error("Menu Error - {} : {}", voucherTypeString, Message.INVALID_VOUCHER_TYPE);
                    return new InvalidDataException(Message.INVALID_VOUCHER_TYPE);
                });
    }

    public String getTypeOrdinal() {
        return typeOrdinal;
    }

    public String getTypeString() {
        return typeString;
    }
}
