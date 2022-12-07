package com.example.springbootbasic.domain.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.time.LocalDateTime;

import static com.example.springbootbasic.domain.voucher.VoucherType.FIXED_AMOUNT;
import static com.example.springbootbasic.exception.voucher.FixedAmountVoucherExceptionMessage.FIXED_AMOUNT_DISCOUNT_RANGE_EXCEPTION;

public class FixedAmountVoucher extends Voucher {
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);
    private static final Long MAX_DISCOUNT_VALUE = 50_000L;
    private static final Long MIN_DISCOUNT_VALUE = 0L;

    public FixedAmountVoucher(Long discountValue,
                              LocalDateTime createdAt,
                              LocalDateTime startAt,
                              LocalDateTime endAt
    ) {
        super(discountValue, FIXED_AMOUNT, createdAt, startAt, endAt);
        validateDiscountValue(discountValue);
    }

    public FixedAmountVoucher(Long voucherId,
                              Long discountValue,
                              LocalDateTime createdAt,
                              LocalDateTime startAt,
                              LocalDateTime endAt
    ) {
        super(voucherId, discountValue, FIXED_AMOUNT, createdAt, startAt, endAt);
        validateDiscountValue(discountValue);
    }

    private void validateDiscountValue(Long discountValue) {
        if (MIN_DISCOUNT_VALUE > discountValue || discountValue > MAX_DISCOUNT_VALUE) {
            logger.error("Fail - {}", MessageFormat.format(FIXED_AMOUNT_DISCOUNT_RANGE_EXCEPTION.getMessage(), MIN_DISCOUNT_VALUE, MAX_DISCOUNT_VALUE));
            throw new IllegalArgumentException(
                    MessageFormat.format(FIXED_AMOUNT_DISCOUNT_RANGE_EXCEPTION.getMessage(), MIN_DISCOUNT_VALUE, MAX_DISCOUNT_VALUE));
        }
    }
}
