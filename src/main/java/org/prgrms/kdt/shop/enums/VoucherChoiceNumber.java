package org.prgrms.kdt.shop.enums;

import java.util.Arrays;

public enum VoucherChoiceNumber {
    FIXED_AMOUNT("1"), PERCENT_DISCOUNT("2");

    private final String inputVoucher;

    VoucherChoiceNumber(String inputVoucher) {
        this.inputVoucher = inputVoucher;
    }

    public static VoucherChoiceNumber find(String inputVoucher) {
        return Arrays.stream(values()).filter(voucherStatus -> voucherStatus.inputVoucher.equals(inputVoucher)).findAny().orElseThrow(( ) -> new IllegalArgumentException("\n[ERROR] 제대로 된 문자를 입력하세요."));
    }
}
