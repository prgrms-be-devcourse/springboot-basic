package com.prgmrs.voucher.enums;

import com.prgmrs.voucher.exception.NoSuchVoucherTypeException;

public enum VoucherSelectionType {
    FIXED_AMOUNT_VOUCHER("fixed"),
    PERCENT_DISCOUNT_VOUCHER("percent");
    private final String value;

    VoucherSelectionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static VoucherSelectionType of(String value) throws NoSuchVoucherTypeException {
        for (VoucherSelectionType enumValue : VoucherSelectionType.values()) {
            if (enumValue.getValue().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new NoSuchVoucherTypeException("no such voucher type");
    }
}
