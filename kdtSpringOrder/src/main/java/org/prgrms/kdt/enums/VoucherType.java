package org.prgrms.kdt.enums;

public enum VoucherType {
    UNDEFINED,
    FIXED,
    DISCOUNT;

    public static VoucherType getVoucherType(String voucherType){
        try {
            return VoucherType.valueOf(voucherType);
        } catch (IllegalArgumentException ex){
            return UNDEFINED;
        }
    }
}