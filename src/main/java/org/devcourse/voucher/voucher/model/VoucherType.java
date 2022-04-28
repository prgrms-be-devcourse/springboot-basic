package org.devcourse.voucher.voucher.model;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("1"),
    PERCENT_DISCOUNT_VOUCHER("2"),
    NONE("");

    private String option;

    VoucherType(String option) {
        this.option = option;
    }

    public static VoucherType discriminate(String input) {
        VoucherType voucherType;

        switch (input) {
            case "1" -> voucherType = FIXED_AMOUNT_VOUCHER;
            case "2" -> voucherType = PERCENT_DISCOUNT_VOUCHER;
            default -> voucherType = NONE;
        }
        return voucherType;
    }
}
