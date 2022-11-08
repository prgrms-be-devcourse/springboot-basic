package org.prgrms.kdtspringdemo.voucher.model;

public enum VoucherType {
    FIXED, PERCENT;
    public static VoucherType getTypeByName(String string) throws IllegalArgumentException {
        try{
            return VoucherType.valueOf(string.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("잘못된 바우쳐 타입 입니다.");
        }
    }
}
