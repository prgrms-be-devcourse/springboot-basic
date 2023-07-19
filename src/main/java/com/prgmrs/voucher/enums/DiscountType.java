package com.prgmrs.voucher.enums;

import com.prgmrs.voucher.exception.WrongRangeFormatException;

public enum DiscountType {
    FIXED_AMOUNT_DISCOUNT("fixed", (short) 1),
    PERCENT_DISCOUNT("percent", (short) 2);

    private final String stringValue;
    private final short shortValue;

    DiscountType(String stringValue, short shortValue) {
        this.stringValue = stringValue;
        this.shortValue = shortValue;
    }

    public static DiscountType fromString(String value) {
        for (DiscountType enumValue : DiscountType.values()) {
            if (enumValue.stringValue.equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new WrongRangeFormatException("no matching discount type from string");
    }

    public static DiscountType fromShort(short value) {
        for (DiscountType enumValue : DiscountType.values()) {
            if (enumValue.shortValue == value) {
                return enumValue;
            }
        }
        throw new WrongRangeFormatException("no matching discount type from short");
    }

    public static short fromEnumValueStringToShortValue(String value) {
        for (DiscountType enumValue : DiscountType.values()) {
            if (enumValue.name().equalsIgnoreCase(value)) {
                return enumValue.shortValue;
            }
        }
        throw new WrongRangeFormatException("no matching discount type from enum");
    }

    public short getShortValue() {
        return shortValue;
    }
}
