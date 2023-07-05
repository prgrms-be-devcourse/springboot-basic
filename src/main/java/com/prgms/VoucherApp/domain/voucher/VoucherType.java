package com.prgms.VoucherApp.domain.voucher;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum VoucherType {
    FIXED_VOUCHER("fix"),
    PERCENT_VOUCHER("percent");

    private final String voucherTypeName;
    private static final Map<String, VoucherType> VOUCHER_POLICY_MAP = Collections.unmodifiableMap(Arrays.stream(values())
        .collect(Collectors.toMap(VoucherType::getVoucherTypeName, Function.identity())));

    VoucherType(String voucherTypeName) {
        this.voucherTypeName = voucherTypeName;
    }

    public String getVoucherTypeName() {
        return this.voucherTypeName;
    }

    public static VoucherType findByVoucherTypeName(String voucherTypeName) {
        return VOUCHER_POLICY_MAP.get(voucherTypeName);
    }

    public static boolean containsVoucherType(String voucherTypeName) {
        return VOUCHER_POLICY_MAP.containsKey(voucherTypeName);
    }

    public boolean isFixedVoucher() {
        return this == FIXED_VOUCHER;
    }
}
