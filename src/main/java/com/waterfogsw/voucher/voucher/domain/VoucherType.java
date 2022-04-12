package com.waterfogsw.voucher.voucher.domain;

import com.waterfogsw.voucher.exception.InvalidVoucherTypeException;

public enum VoucherType {
    FIXED_AMOUNT(1),
    PERCENT_DISCOUNT(2);

    private final int typeNum;

    VoucherType(int typeNum) {
        this.typeNum = typeNum;
    }

    public static VoucherType getType(String typeNum) throws InvalidVoucherTypeException {
        return null;
    }
}
