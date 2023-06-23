package org.prgms.voucher.view;


import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum VoucherPolicy {
    FIXED_AMOUNT("FixedAmount"),
    PERCENT_DISCOUNT("PercentDiscount");

    private static final Map<String, VoucherPolicy> VOUCHER_POLICY_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(VoucherPolicy::getVoucherPolicy, Function.identity())));

    private final String voucherPolicy;

    VoucherPolicy(String voucherPolicy) {
        this.voucherPolicy = voucherPolicy;
    }

    public static VoucherPolicy find(String voucherPolicy) {
        System.out.println("voucherPolicy = " + voucherPolicy);
        if (!VOUCHER_POLICY_MAP.containsKey(voucherPolicy)) {
            throw new NoSuchElementException("유효하지 않는 바우처입니다.");
        }
        return VOUCHER_POLICY_MAP.get(voucherPolicy);
    }
}
