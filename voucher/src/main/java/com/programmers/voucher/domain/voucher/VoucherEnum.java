package com.programmers.voucher.domain.voucher;

public enum VoucherEnum {
    FIXED(1), PERCENT(2);

    private final int number;

    VoucherEnum(int number) {
        this.number = number;
    }

    public static VoucherEnum decideVoucherType(int number) {
        if (FIXED.number == number) {
            return FIXED;
        }
        if (PERCENT.number == number) {
            return PERCENT;
        }
        throw new IllegalArgumentException("지원하지 않는 버전입니다. 버전을 다시 확인 해주세요.");
    }
}
