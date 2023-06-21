package com.dev.voucherproject.model.voucher;

import java.util.Arrays;

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
                .filter(v -> v.isExistPolicy(input))
                .findFirst().orElseThrow(() -> new RuntimeException("Invalid input value."));
    }
}
