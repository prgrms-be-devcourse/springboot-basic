package com.example.springbootbasic.domain.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

import static com.example.springbootbasic.domain.voucher.VoucherType.PERCENT_DISCOUNT;
import static com.example.springbootbasic.exception.voucher.PercentDiscountVoucherExceptionMessage.PERCENT_DISCOUNT_RANGE_EXCEPTION;

public class PercentDiscountVoucher extends Voucher {

    private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);
    private static final Long MAX_DISCOUNT_PERCENT = 100L;
    private static final Long MIN_DISCOUNT_PERCENT = 0L;

    public PercentDiscountVoucher(Long voucherId, Long discountValue) {
        super(voucherId, discountValue, PERCENT_DISCOUNT);
        validateDiscountValue(discountValue);
    }

    public VoucherType getVoucherType() {
        return PERCENT_DISCOUNT;
    }

    private void validateDiscountValue(Long discountValue) {
        if (MIN_DISCOUNT_PERCENT > discountValue || discountValue > MAX_DISCOUNT_PERCENT) {
            logger.error("Fail - {}", MessageFormat.format(PERCENT_DISCOUNT_RANGE_EXCEPTION.getMessage(), MIN_DISCOUNT_PERCENT, MAX_DISCOUNT_PERCENT));
            throw new IllegalArgumentException(
                    MessageFormat.format(PERCENT_DISCOUNT_RANGE_EXCEPTION.getMessage(), MIN_DISCOUNT_PERCENT, MAX_DISCOUNT_PERCENT));
        }
    }
}
