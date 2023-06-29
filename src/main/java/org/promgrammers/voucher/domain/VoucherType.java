package org.promgrammers.voucher.domain;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum VoucherType {
    FixedAmount("fixed"),
    PercentDiscount("percent");

    private final String value;


    public static VoucherType fromType(String value) {
        for (VoucherType type : VoucherType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 Voucher 타입: " + value);
    }
}

