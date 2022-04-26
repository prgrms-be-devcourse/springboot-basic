package org.prgrms.kdt.model.voucher;

import org.prgrms.kdt.model.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    private static final int MAX_DISCOUNT_PERCENT = 100;

    private final static Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);

    public PercentDiscountVoucher(UUID voucherId, long discountPercent, LocalDateTime createAt) {
        super(voucherId, discountPercent, createAt, VoucherType.PERCENT_DISCOUNT.getTypeNumber());
    }

    public PercentDiscountVoucher(UUID voucherId, long discountPercent, LocalDateTime createAt, Customer customer, LocalDateTime ownedAt) {
        super(voucherId, discountPercent, createAt, customer, ownedAt, VoucherType.FIXED_AMOUNT.getTypeNumber());
    }

    @Override
    void validateDiscountAmount(long discountPercent) {
        if (discountPercent <= 0) {
            logger.info("input [discountPercent] -> {}", discountPercent);
            throw new IllegalArgumentException("discountPercent should be over 0");
        }
        if (discountPercent > MAX_DISCOUNT_PERCENT) {
            logger.info("input [discountPercent] -> {}", discountPercent);
            throw new IllegalArgumentException("Max discountPercent is 100");
        }
    }
}
