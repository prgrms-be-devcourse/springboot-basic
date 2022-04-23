package org.prgrms.kdt.shop.enums;

import java.util.Arrays;

public enum VoucherType {

    FIXED_AMOUNT("1"), PERCENT_DISCOUNT("2");

    private final String inputVoucher;

    VoucherType(String inputVoucher) {
        this.inputVoucher = inputVoucher;
    }

    public static VoucherType find(String inputVoucher) {
        return Arrays.stream(values()).filter(voucherType -> voucherType.inputVoucher.equals(inputVoucher)).findAny().orElseThrow(( ) -> new IllegalArgumentException("\n[ERROR] 제대로 된 문자를 입력하세요."));
    }

    public String getInputVoucher( ) {
        return this.inputVoucher;
    }
}
