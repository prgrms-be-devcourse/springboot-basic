package com.dev.voucherproject.model.voucher;


import java.util.Arrays;
import java.util.List;

public enum VoucherPolicy {
    FIXED_AMOUNT_VOUCHER("fix"),
    PERCENT_DISCOUNT_VOUCHER("per");

    private final String policyName;

    VoucherPolicy(String policyName) {
        this.policyName = policyName;
    }

    private boolean isExistPolicy(String input) {
        return this.policyName.equals(input);
    }

    public static VoucherPolicy convertStringInputToPolicy(String input) {
        return Arrays.stream(VoucherPolicy.values())
                .filter(voucherPolicy -> voucherPolicy.isExistPolicy(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("입력에 해당하는 바우처 정책을 찾을 수 없습니다."));
    }

    public static List<String> getPolicyNames() {
        return Arrays.stream(VoucherPolicy.values())
                .map(voucherPolicy -> voucherPolicy.policyName)
                .toList();
    }
}
