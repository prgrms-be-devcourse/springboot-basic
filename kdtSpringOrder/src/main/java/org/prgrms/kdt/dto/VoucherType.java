package org.prgrms.kdt.dto;

public enum VoucherType {
    UNDEFINED("undefined"),
    FIXED("fixed"),
    DISCOUNT("discount");

    VoucherType(String voucherType) {
    }

    static public VoucherType getVoucherType(String voucherType){
        try {
            return VoucherType.valueOf(voucherType);
        } catch (IllegalArgumentException ex){
            return UNDEFINED;
        }
    }
}