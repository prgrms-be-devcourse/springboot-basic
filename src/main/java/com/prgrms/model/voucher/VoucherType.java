package com.prgrms.model.voucher;

import com.prgrms.io.ErrorMessage;

import java.util.Arrays;
import java.util.Optional;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("1",
            "얼만큼 할인 받고 싶은지 입력하세요 :"),
    PERCENT_DISCOUNT_VOUCHER("2",
            "할인율을 입력하세요 :");

    private final String number;
    private final String discountGuide;

    VoucherType(String number, String discountGuide) {
        this.number = number;
        this.discountGuide = discountGuide;
    }

    public static VoucherType findByPolicy(String policy) {
        return Arrays.stream(VoucherType.values())
                .filter(p -> p.number.equals(policy))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException(ErrorMessage.INVALID_SELECTION.getMessage()));
    }

    public String voucherPolicyOptionGuide() {
        return number + "번 : " + name();
    }

    public String discountGuide() {
        return discountGuide;
    }

}
