package com.prgmrs.voucher.enums;

import com.prgmrs.voucher.exception.NoSuchVoucherTypeException;

public enum DiscountType {
    FIXED_AMOUNT_DISCOUNT((short) 1),
    PERCENT_DISCOUNT((short) 2);
    private final short value;

    DiscountType(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }

    public static short fromEnumValue(String value) throws NoSuchVoucherTypeException {
        for (DiscountType enumValue : DiscountType.values()) {
            if (enumValue.name().equalsIgnoreCase(value)) {
                return enumValue.getValue();
            }
        }
        throw new NoSuchVoucherTypeException("no such discount type");
    }

    public static DiscountType fromValue(short value) throws NoSuchVoucherTypeException {
        for (DiscountType enumValue : DiscountType.values()) {
            if (enumValue.getValue() == value) {
                return enumValue;
            }
        }
        throw new NoSuchVoucherTypeException("no such discount type");
    }
}
