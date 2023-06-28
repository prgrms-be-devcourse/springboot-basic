package com.devcourse.voucher.application;

import com.devcourse.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VoucherValidator {
    private static final int MIN_DISCOUNT = 0;
    private static final int MAX_DISCOUNT = 100;

    public void validateDiscountAmount(int discountAmount) {
        if (discountAmount <= MIN_DISCOUNT) {
            throw new IllegalArgumentException("Discount Amount cannot be smaller than zero.");
        }
    }

    public void validateDiscountRate(int discountRate) {
        if (discountRate <= MIN_DISCOUNT || MAX_DISCOUNT < discountRate) {
            throw new IllegalArgumentException("Discount Rate must be bigger than ZERO and smaller than HUNNIT");
        }
    }

    public void validateExpiration(LocalDateTime expiredAt) {
        LocalDateTime now = LocalDateTime.now();

        if (expiredAt.isBefore(now)) {
            throw new IllegalArgumentException("Expiration time cannot be the past.");
        }
    }

    public void validate(Voucher voucher) {
        isExpired(voucher);
        isUsed(voucher);
    }

    private void isExpired(Voucher voucher) {
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(voucher.getExpireAt())) {
            throw new IllegalStateException("This voucher is expired");
        }
    }

    private void isUsed(Voucher voucher) {
        if (voucher.isUsed()) {
            throw new IllegalStateException("This voucher is already used");
        }
    }
}
