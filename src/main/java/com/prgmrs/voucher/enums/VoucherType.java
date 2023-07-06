package com.prgmrs.voucher.enums;

import com.prgmrs.voucher.exception.NoSuchVoucherTypeException;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("fixed"),
    PERCENT_DISCOUNT_VOUCHER("percent");
    private final String value;

    VoucherType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static VoucherType of(String value) throws NoSuchVoucherTypeException {
        for (VoucherType enumValue : VoucherType.values()) {
            if (enumValue.getValue().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new NoSuchVoucherTypeException("no such voucher type");
    }
}
