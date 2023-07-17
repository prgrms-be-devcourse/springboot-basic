package com.dev.voucherproject.model.voucher;


import com.fasterxml.jackson.annotation.JsonCreator;

import java.text.MessageFormat;
import java.util.Arrays;

public enum VoucherPolicy {
    FIXED_AMOUNT_VOUCHER(FixedAmountVoucher.class.getSimpleName()),
    PERCENT_DISCOUNT_VOUCHER(PercentDiscountVoucher.class.getSimpleName());

    private final String policyName;

    VoucherPolicy(String policyName) {
        this.policyName = policyName;
    }

    private boolean isExistPolicy(String input) {
        return this.policyName.equals(input);
    }

    public static VoucherPolicy convertPolicyNameToPolicy(String policyName) {
        return Arrays.stream(VoucherPolicy.values())
                .filter(voucherPolicy -> voucherPolicy.isExistPolicy(policyName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("{0} 에 해당하는 바우처 정책을 찾을 수 없습니다.", policyName)));
    }

    @JsonCreator
    public static VoucherPolicy from(String voucherPolicy) {
        return VoucherPolicy.valueOf(voucherPolicy);
    }
}
