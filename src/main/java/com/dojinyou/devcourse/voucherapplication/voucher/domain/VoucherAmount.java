package com.dojinyou.devcourse.voucherapplication.voucher.domain;

public abstract class VoucherAmount {
    public static final String ERROR_MESSAGE_NOT_FOUND_VOUCHER_TYPE = "잘못된 바우처 타입입니다.";
    private final int value;

    public VoucherAmount(int value) {
        this.value = value;
    }

    public static VoucherAmount of(VoucherType voucherType, int userInput) {
        if (voucherType == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NOT_FOUND_VOUCHER_TYPE);
        }
        switch (voucherType) {
            case FIXED:
                return new FixedAmount(userInput);
            case PERCENT:
                return new PercentAmount(userInput);
            default:
                throw new IllegalArgumentException(ERROR_MESSAGE_NOT_FOUND_VOUCHER_TYPE);
        }
    }

    public int getValue() {
        return this.value;
    }

    public abstract void validate(int amount);
}
