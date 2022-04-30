package com.pppp0722.vouchermanagement.voucher.model;

public enum VoucherType {

    FIXED_AMOUNT,
    PERCENT_DISCOUNT,
    NONE;

    public static VoucherType getVoucherType(String type) {
        VoucherType voucherType;
        type = type.toLowerCase();
        switch (type) {
            case "fixed":
            case "fixed_amount":
                voucherType = VoucherType.FIXED_AMOUNT;
                break;
            case "percent":
            case "percent_amount":
                voucherType = VoucherType.PERCENT_DISCOUNT;
                break;
            default:
                voucherType = VoucherType.NONE;
                break;
        }

        return voucherType;
    }
}
