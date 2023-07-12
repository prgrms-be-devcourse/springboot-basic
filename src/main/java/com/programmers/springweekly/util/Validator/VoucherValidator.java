package com.programmers.springweekly.util.Validator;

import com.programmers.springweekly.domain.voucher.VoucherType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VoucherValidator {

    private static final Pattern numberPattern = Pattern.compile("\\d+");
    private static final int PERCENT_MAX = 100;
    private static final int PERCENT_AND_FIXED_MIN = 0;

    public static void validateInputVoucherInfo(VoucherType voucherType, String discountAmount) {
        if (voucherType == VoucherType.FIXED) {
            validateNumber(discountAmount);
            validateFixedRange(discountAmount);
        }

        if (voucherType == VoucherType.PERCENT) {
            validateNumber(discountAmount);
            validatePercentRange(discountAmount);
        }
    }

    private static void validateNumber(String input) {
        Matcher match = numberPattern.matcher(input);

        if (!match.matches()) {
            throw new IllegalArgumentException("Input : " + input + ", 입력하신 것은 숫자가 아닙니다.");
        }
    }

    private static void validateFixedRange(String inputFixed) {
        int fixed = Integer.parseInt(inputFixed);

        if (fixed < PERCENT_AND_FIXED_MIN) {
            throw new IllegalArgumentException("Input : " + inputFixed + ", 입력하신 숫자는 범위를 벗어납니다.");
        }
    }

    private static void validatePercentRange(String inputPercent) {
        int percent = Integer.parseInt(inputPercent);

        if (percent > PERCENT_MAX || percent < PERCENT_AND_FIXED_MIN) {
            throw new IllegalArgumentException("Input : " + inputPercent + ", 입력하신 숫자는 범위를 벗어납니다.");
        }
    }

}
