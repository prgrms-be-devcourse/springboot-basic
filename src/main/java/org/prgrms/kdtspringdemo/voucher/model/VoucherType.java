package org.prgrms.kdtspringdemo.voucher.model;

public enum VoucherType {
    FIXED, PERCENT;
    public static VoucherType getTypeByName(String string) throws IllegalArgumentException {
        return VoucherType.valueOf(string.toUpperCase());
    }
}
