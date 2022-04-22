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
                voucherType = VoucherType.FIXED_AMOUNT;
                break;
            case "percent":
                voucherType = VoucherType.PERCENT_DISCOUNT;
                break;
            default:
                voucherType = VoucherType.NONE;
                break;
        }

        return voucherType;
    }
}
