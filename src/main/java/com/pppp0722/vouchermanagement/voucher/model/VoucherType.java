package com.pppp0722.vouchermanagement.voucher.model;

public enum VoucherType {

    FIXED_AMOUNT,
    PERCENT_DISCOUNT,
    NONE;

    public static VoucherType getVoucherType(String type) {
        switch (type.toLowerCase()) {
            case "fixed":
            case "fixed_amount":
                return VoucherType.FIXED_AMOUNT;
            case "percent":
            case "percent_amount":
                return VoucherType.PERCENT_DISCOUNT;
            default:
                return VoucherType.NONE;
        }
    }
}
