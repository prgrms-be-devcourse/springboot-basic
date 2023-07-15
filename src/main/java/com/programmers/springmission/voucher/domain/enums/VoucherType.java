package com.programmers.springmission.voucher.domain.enums;

import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.InvalidInputException;
import com.programmers.springmission.voucher.domain.FixedAmountPolicy;
import com.programmers.springmission.voucher.domain.PercentDiscountPolicy;
import com.programmers.springmission.voucher.domain.VoucherPolicy;

import java.util.Arrays;

public enum VoucherType {

    FIXED_AMOUNT("fixed"),
    PERCENT_DISCOUNT("percent");

    private static final VoucherType[] VOUCHER_POLICIES = VoucherType.values();

    private final String policy;

    VoucherType(String policy) {
        this.policy = policy;
    }

    public static VoucherType of(String inputPolicy) {
        return Arrays.stream(VOUCHER_POLICIES)
                .filter(voucherPolicy -> voucherPolicy.policy.equals(inputPolicy))
                .findFirst()
                .orElseThrow(() -> new InvalidInputException(ErrorMessage.INVALID_VOUCHER_TYPE));
    }

    public static VoucherPolicy mapperVoucherPolicy(VoucherType voucherType) {
        return switch (voucherType) {
            case FIXED_AMOUNT -> new FixedAmountPolicy(voucherType);
            case PERCENT_DISCOUNT -> new PercentDiscountPolicy(voucherType);
        };
    }
}

