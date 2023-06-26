package com.prgrms.model.voucher;

import java.util.Arrays;
import java.util.Optional;

public enum VoucherPolicy {
    FixedAmountVoucher("1",
            "고정 금액 할인에 따른 바우처",
            "얼만큼 할인 받고 싶은지 입력하세요 (단위: 원):",
            "원"),
    PercentDiscountVoucher("2",
            "할인율에 따른 바우처",
            "할인율을 입력하세요 (0~100 사이의 값):",
            "%");

    private final String number;
    private final String policy;
    private final String discountGuide;
    private final String unit;

    VoucherPolicy(String number, String policy, String discountGuide, String unit) {
        this.number = number;
        this.policy = policy;
        this.discountGuide = discountGuide;
        this.unit = unit;
    }

    public static Optional<VoucherPolicy> findBySelectedPolicy(String selectedPolicy) {
        return Arrays.stream(VoucherPolicy.values())
                .filter(p -> p.number.equals(selectedPolicy))
                .findFirst();
    }

    public String voucherPolicyOptionGuid() {
        return number + "번 : " + policy;
    }

    public String discountGuide() {
        return discountGuide;
    }

    public String getUnit() {
        return unit;
    }
}
