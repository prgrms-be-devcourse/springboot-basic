package com.devcourse.voucher.application;

import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.domain.Voucher;
import com.devcourse.voucher.domain.VoucherType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VoucherValidator {
    private static final int MIN_DISCOUNT = 0;
    private static final int MAX_DISCOUNT = 100;

    public void validateRequest(CreateVoucherRequest request) {
        String inputSymbol = request.typeSymbol();

        validateVoucherType(inputSymbol);
        validateExpiration(request.expiredAt());

        if (VoucherType.isFixed(inputSymbol)) {
            validateFixedAmount(request.discount());
        }

        validatePercentRate(request.discount());
    }

    public void isUsable(Voucher voucher) {
        isExpired(voucher);
        isUsed(voucher);
    }

    private void validateVoucherType(String symbol) {
        if (VoucherType.isIncorrectSymbol(symbol)) {
            throw new IllegalArgumentException("Your input is incorrect. Type correctly");
        }
    }

    private void validateFixedAmount(int discountAmount) {
        if (discountAmount <= MIN_DISCOUNT) {
            throw new IllegalArgumentException("Discount Amount cannot be smaller than zero.");
        }
    }

    private void validatePercentRate(int discountRate) {
        if (discountRate <= MIN_DISCOUNT || MAX_DISCOUNT < discountRate) {
            throw new IllegalArgumentException("Discount Rate must be bigger than ZERO and smaller than HUNNIT");
        }
    }

    private void validateExpiration(LocalDateTime expiredAt) {
        LocalDateTime now = LocalDateTime.now();

        if (expiredAt.isBefore(now)) {
            throw new IllegalArgumentException("Expiration time cannot be the past.");
        }
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
