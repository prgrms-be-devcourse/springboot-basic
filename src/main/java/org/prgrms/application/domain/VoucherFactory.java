package org.prgrms.application.domain;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {

    public static Voucher create(VoucherType voucherType, String voucherDetails) {
        switch (voucherType) {
            case FIXED:
                long discountAmount = parsePositiveNum(voucherDetails);
                return new FixedAmountVoucher(UUID.randomUUID(), discountAmount);

            case PERCENT:
                float discountPercent = parsePercent(voucherDetails);
                return new PercentAmountVoucher(UUID.randomUUID(), discountPercent);

            default:
                throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

    private static long parsePositiveNum(String voucherDetails) {
        try {
            long discountAmount = Long.parseLong(voucherDetails);
            if (discountAmount <= 0) {
                throw new IllegalArgumentException("양수를 입력하세요");
            }
            return discountAmount;
        }

        catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static float parsePercent(String voucherDetails) {
        if (!voucherDetails.endsWith("%")) {
            throw new IllegalArgumentException("정확한 할인율을 입력해주세요 : %사용 ");
        }

        String numericValue = voucherDetails.substring(0, voucherDetails.length() - 1);
        try {
            float percent = Float.parseFloat(numericValue);
            if (percent <= 0 || percent >= 100) {
                throw new IllegalArgumentException("잘못된 입력 범위입니다.");
            }
            return percent;
        }

        catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 입력 형식입니다.");
        }
    }
}
