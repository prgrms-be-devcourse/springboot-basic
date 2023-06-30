package com.devcourse.voucher.application;

import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.domain.Voucher;
import com.devcourse.voucher.domain.VoucherType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VoucherValidator {
    public static final String NOT_SUPPORT_TYPE = "[Error] Your Input Is Not Support Type. Input : ";
    public static final String INVALID_DISCOUNT_AMOUNT = "[Error] Discount Amount MUST Be Bigger Than Zero. Input : ";
    public static final String INVALID_DISCOUNT_RATE = "[Error] Discount Rate MUST Be Bigger Than ZERO, Smaller Than Hunnit. Input : ";
    public static final String INVALID_EXPIRATION_TIME = "[Error] Expiration Time Cannot Be The Past. Input : ";
    public static final String EXPIRED_VOUCHER = "[Error] This Voucher Is EXPIRED";
    public static final String USED_VOUCHER = "[Error] This Voucher Is Already USED";
    private static final int MAX_DISCOUNT = 100;
    private static final int MIN_DISCOUNT = 0;

    public void validateRequest(CreateVoucherRequest request) {
        String inputSymbol = request.typeSymbol();

        validateVoucherType(inputSymbol);
        validateExpiration(request.expiredAt());

        if (VoucherType.isFixType(inputSymbol)) {
            validateFixedAmount(request.discount());
        } else {
            validatePercentRate(request.discount());
        }
    }

    public void isUsable(Voucher voucher) {
        isExpired(voucher);
        isUsed(voucher);
    }

    private void validateVoucherType(String symbol) {
        if (VoucherType.isIncorrectType(symbol)) {
            throw new IllegalArgumentException(NOT_SUPPORT_TYPE + symbol);
        }
    }

    private void validateFixedAmount(int discountAmount) {
        if (discountAmount <= MIN_DISCOUNT) {
            throw new IllegalArgumentException(INVALID_DISCOUNT_AMOUNT + discountAmount);
        }
    }

    private void validatePercentRate(int discountRate) {
        if (discountRate <= MIN_DISCOUNT || MAX_DISCOUNT < discountRate) {
            throw new IllegalArgumentException(INVALID_DISCOUNT_RATE + discountRate);
        }
    }

    private void validateExpiration(LocalDateTime expiredAt) {
        LocalDateTime now = LocalDateTime.now();

        if (expiredAt.isBefore(now)) {
            throw new IllegalArgumentException(INVALID_EXPIRATION_TIME + expiredAt);
        }
    }

    private void isExpired(Voucher voucher) {
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(voucher.getExpireAt())) {
            throw new IllegalStateException(EXPIRED_VOUCHER);
        }
    }

    private void isUsed(Voucher voucher) {
        if (voucher.isUsed()) {
            throw new IllegalStateException(USED_VOUCHER);
        }
    }
}
