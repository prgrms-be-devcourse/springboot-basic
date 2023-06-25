package com.programmers.voucher.domain.voucher;

public enum VoucherEnum {
    FIXED(1), PERCENT(2);

    private final int numberOfVoucherEnum;

    VoucherEnum(int numberOfVoucherEnum) {
        this.numberOfVoucherEnum = numberOfVoucherEnum;
    }

    public static VoucherEnum decideVoucherType(int number) {
        if (FIXED.numberOfVoucherEnum == number) {
            return FIXED;
        }
        if (PERCENT.numberOfVoucherEnum == number) {
            return PERCENT;
        }
        throw new IllegalArgumentException("지원하지 않는 버전입니다. 버전을 다시 확인 해주세요.");
    }
}
