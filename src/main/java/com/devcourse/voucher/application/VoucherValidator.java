package com.devcourse.voucher.application;

import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.domain.Voucher;
import com.devcourse.voucher.domain.VoucherType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VoucherValidator {
    private static final String NEGATIVE_DISCOUNT = "[Error] Discount Value MUST Be Positive. Input : ";
    private static final String OUT_RANGED_DISCOUNT = "[Error] Discount Rate MUST Be Smaller Than 100. Input : ";
    private static final String INVALID_EXPIRATION = "[Error] Expiration Time Cannot Be The Past. Input : ";
    private static final String EXPIRED_VOUCHER = "[Error] This Voucher Is EXPIRED";
    private static final String USED_VOUCHER = "[Error] This Voucher Is Already USED";
    private static final int MAX_DISCOUNT_RATE = 100;
    private static final int MIN_DISCOUNT = 0;

    public void validateRequest(CreateVoucherRequest request) {
        validateDiscount(request);
        validateExpiration(request.expiredAt());
    }

    public void validateUsable(Voucher voucher) {
        validateExpiration(voucher);
        validateUsed(voucher);
    }

    private void validateDiscount(CreateVoucherRequest request) {
        VoucherType voucherType = request.type();
        int discount = request.discount();

        if (isNegative(discount)) {
            throw new IllegalArgumentException(NEGATIVE_DISCOUNT + discount);
        }

        if (voucherType.isPercent() && isRateOutRange(discount)) {
            throw new IllegalArgumentException(OUT_RANGED_DISCOUNT + discount);
        }
    }

    private void validateExpiration(LocalDateTime expiredAt) {
        LocalDateTime now = LocalDateTime.now();

        if (expiredAt.isBefore(now)) {
            throw new IllegalArgumentException(INVALID_EXPIRATION + expiredAt);
        }
    }

    private boolean isNegative(int discountAmount) {
        return discountAmount <= MIN_DISCOUNT;
    }

    private boolean isRateOutRange(int discountRate) {
        return MAX_DISCOUNT_RATE < discountRate;
    }

    private void validateExpiration(Voucher voucher) {
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(voucher.getExpireAt())) {
            throw new IllegalStateException(EXPIRED_VOUCHER);
        }
    }

    private void validateUsed(Voucher voucher) {
        if (voucher.isUsed()) {
            throw new IllegalStateException(USED_VOUCHER);
        }
    }
}
