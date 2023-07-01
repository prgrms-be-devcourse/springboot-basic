package com.prgrms.model.voucher;

import java.util.Arrays;
import java.util.Optional;

public enum VoucherPolicy {
    FIXED_AMOUNT_VOUCHER("1",
            "얼만큼 할인 받고 싶은지 입력하세요 (단위: 원):",
            "원"),
    PERCENT_DISCOUNT_VOUCHER("2",
            "할인율을 입력하세요 (0~100 사이의 값):",
            "%");

    private final String number;
    private final String discountGuide;
    private final String unit;

    VoucherPolicy(String number, String discountGuide, String unit) {
        this.number = number;
        this.discountGuide = discountGuide;
        this.unit = unit;
    }

    public static Optional<VoucherPolicy> findByPolicy(String policy) {
        return Arrays.stream(VoucherPolicy.values())
                .filter(p -> p.number.equals(policy))
                .findFirst();
    }

    public String voucherPolicyOptionGuide() {
        return number + "번 : " + name();
    }

    public String discountGuide() {
        return discountGuide;
    }

    public String getUnit() {
        return unit;
    }
}
