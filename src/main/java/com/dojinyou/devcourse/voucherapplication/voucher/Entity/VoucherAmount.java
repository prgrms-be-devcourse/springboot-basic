package com.dojinyou.devcourse.voucherapplication.voucher.Entity;

public interface VoucherAmount {
    String ERROR_MESSAGE_NOT_FOUND_VOUCHER_TYPE = "잘못된 바우처 타입입니다.";

    static VoucherAmount of(VoucherType voucherType, int userInput) {
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

    void validate(int amount);
}
