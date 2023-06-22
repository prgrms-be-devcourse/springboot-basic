package org.prgrms.kdt.voucher.utils;

public enum VoucherType {
    Fixed_AMOUNT_VOUCHER(1), PERCENT_DISCOUNT_VOUCHER(2);

    private int matchNum;

    VoucherType(int matchNum) {
        this.matchNum = matchNum;
    }

    public static VoucherType of(String input) {

        for (VoucherType voucherType : values()) {
            if (voucherType.matchNum == Integer.parseInt(input)) {
                return voucherType;
            }
        }
        throw new RuntimeException("입력이 잘못되었습니다.");
    }
}
