package com.prgrms.springbootbasic.enums;

public enum VoucherType {
    FIXED,
    PERCENT;


    public static VoucherType of(String voucherType) {
        try {
            return VoucherType.valueOf(voucherType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 바우처 타입을 입력하셨습니다. 고정 할인 바우처(fixed) 또는 비율 할인 바우처(percent)를 선택해서 다시 입력해주세요!");
        }
    }
}
