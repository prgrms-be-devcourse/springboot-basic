package com.prgms.VoucherApp.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum VoucherPolicy {
    FIXED_VOUCHER("fix", "a Fixed Amount Voucher", "(0 이상으로 입력해주세요.)"),
    PERCENT_VOUCHER("percent", "a Percent Voucher", "(100 이하로 입력해주세요.)");

    private final String voucherPolicy;
    private final String policyDescription;
    private final String discountCondition;
    private static final Map<String, VoucherPolicy> VOUCHER_POLICY_MAP = Collections.unmodifiableMap(Arrays.stream(values())
            .collect(Collectors.toMap(VoucherPolicy::getVoucherPolicy, Function.identity())));

    VoucherPolicy(String voucherPolicy, String policyDescription, String discountCondition) {
        this.voucherPolicy = voucherPolicy;
        this.policyDescription = policyDescription;
        this.discountCondition = discountCondition;
    }

    public String getVoucherPolicy() {
        return this.voucherPolicy;
    }

    public String getPolicyDescription() {
        return this.policyDescription;
    }

    public String getDiscountCondition() {
        return this.discountCondition;
    }

    public static VoucherPolicy findByPolicy(String policy) {
        return VOUCHER_POLICY_MAP.get(policy);
    }
}
