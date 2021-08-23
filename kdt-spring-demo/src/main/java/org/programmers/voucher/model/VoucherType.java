package org.programmers.voucher.model;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent"),
    INPUT_ERROR("");

    private String inputVoucherType;

    VoucherType(String inputVoucherType) {
        this.inputVoucherType = inputVoucherType;
    }

    public static VoucherType getInputType(String inputVoucherType) {
        for (VoucherType voucherType : VoucherType.values()) {
            if (voucherType.inputVoucherType.equals(inputVoucherType)) {
                return voucherType;
            }
        }

        return INPUT_ERROR;
    }
}
