package com.prgrms.springbootbasic.enums;

public enum VoucherType {
    FIXED,
    PERCENT;


    public static VoucherType of(String voucherType) {
        try {
            return VoucherType.valueOf(voucherType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 바우처 타입입니다." + voucherType);
        }
    }
}
