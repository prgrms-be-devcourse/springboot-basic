package com.programmers.springmission.voucher.domain.enums;

import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.VoucherException;

import java.util.Arrays;

public enum VoucherType {

    FIXED_AMOUNT("1"),
    PERCENT_DISCOUNT("2");

    private static final VoucherType[] VOUCHER_POLICIES = VoucherType.values();

    private final String policy;

    VoucherType(String policy) {
        this.policy = policy;
    }

    public static VoucherType of(String inputPolicy) {
        return Arrays.stream(VOUCHER_POLICIES)
                .filter(voucherPolicy -> voucherPolicy.policy.equals(inputPolicy))
                .findFirst()
                .orElseThrow(() -> new VoucherException(ErrorMessage.INVALID_VOUCHER_TYPE));
    }
}

