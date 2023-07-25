package com.programmers.springweekly.util.validator;

import com.programmers.springweekly.domain.voucher.VoucherType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class VoucherValidator {

    private static final int PERCENT_MAX = 100;
    private static final int PERCENT_AND_FIXED_MIN = 0;

    public static void validateVoucher(VoucherType voucherType, long discountAmount) {

        switch (voucherType) {
            case FIXED -> {
                validateFixedRange(discountAmount);
            }
            case PERCENT -> {
                validatePercentRange(discountAmount);
            }
            default -> {
                log.warn("고객이 입력한 문자열 {} 이 바우처 타입이 아니어서 예외 발생", voucherType);
                throw new IllegalArgumentException("Input : " + voucherType + "입력하신 바우처 타입은 없는 타입입니다.");
            }
        }
    }


    private static void validateFixedRange(long inputFixed) {

        if (inputFixed < PERCENT_AND_FIXED_MIN) {
            log.warn("고객이 입력한 고정 할인 금액 {} 이 음수여서 예외 발생", inputFixed);
            throw new IllegalArgumentException("Input : " + inputFixed + ", 입력하신 숫자는 음수입니다. 양수를 입력해주세요.");
        }
    }

    private static void validatePercentRange(long inputPercent) {

        if (inputPercent > PERCENT_MAX || inputPercent < PERCENT_AND_FIXED_MIN) {
            log.warn("고객이 입력한 퍼센트 할인 {}% 가 퍼센트 범위를 벗어나서 예외 발생", inputPercent);
            throw new IllegalArgumentException("Input : " + inputPercent + ", 입력하신 숫자는 퍼센트 범위를 벗어납니다.");
        }
    }

}
