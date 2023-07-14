package com.programmers.springmission.voucher.domain.enums;

import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.InvalidInputException;
import com.programmers.springmission.voucher.domain.FixedAmountPolicy;
import com.programmers.springmission.voucher.domain.PercentDiscountPolicy;
import com.programmers.springmission.voucher.domain.VoucherPolicy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum VoucherType {

    FIXED_AMOUNT("fixed"),
    PERCENT_DISCOUNT("percent");

    private final String policy;

    private static final Map<String, VoucherType> VOUCHER_TYPE_MAP = Arrays.stream(VoucherType.values())
            .collect(Collectors.toMap(VoucherType::getPolicy, Function.identity()));

    public static VoucherType of(String inputPolicy) {
        VoucherType voucherType = VOUCHER_TYPE_MAP.get(inputPolicy);
        if (voucherType == null) {
            throw new InvalidInputException(ErrorMessage.INVALID_OPTION_TYPE);
        }

        return voucherType;
    }

    public static VoucherPolicy mapperVoucherPolicy(VoucherType voucherType) {
        return switch (voucherType) {
            case FIXED_AMOUNT -> new FixedAmountPolicy(voucherType);
            case PERCENT_DISCOUNT -> new PercentDiscountPolicy(voucherType);
        };
    }
}

