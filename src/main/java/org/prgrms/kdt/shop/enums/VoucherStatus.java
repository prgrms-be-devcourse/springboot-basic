package org.prgrms.kdt.shop.enums;

import java.util.Arrays;

public enum VoucherStatus {
    FIXED_AMOUNT("1"), PERCENT_DISCOUNT("2");

    private String inputVoucher;

    VoucherStatus(String inputVoucher) {
        this.inputVoucher = inputVoucher;
    }

    public static VoucherStatus find(String inputVoucher) {
        return Arrays.stream(values()).filter(voucherStatus -> voucherStatus.inputVoucher.equals(inputVoucher)).findAny().orElseThrow(( ) -> new IllegalArgumentException("\n[ERROR] 제대로 된 문자를 입력하세요."));
    }
}
