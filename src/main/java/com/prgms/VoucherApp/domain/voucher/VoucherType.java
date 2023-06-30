package com.prgms.VoucherApp.domain.voucher;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum VoucherType {
    FIXED_VOUCHER("fix"),
    PERCENT_VOUCHER("percent");

    private final String voucherType;
    private static final Map<String, VoucherType> VOUCHER_POLICY_MAP = Collections.unmodifiableMap(Arrays.stream(values())
            .collect(Collectors.toMap(VoucherType::getVoucherPolicy, Function.identity())));

    VoucherType(String voucherPolicy) {
        this.voucherType = voucherPolicy;
    }

    public String getVoucherPolicy() {
        return this.voucherType;
    }

    public static VoucherType findByPolicy(String policy) {
        return VOUCHER_POLICY_MAP.get(policy);
    }

    public static boolean containsVoucherPolicy(String policy) {
        return VOUCHER_POLICY_MAP.containsKey(policy);
    }

    public static String[] getAllVoucherType() {
        return VOUCHER_POLICY_MAP.keySet().toArray(String[]::new);
    }
}
