package org.prgrms.kdt.model.voucher;

import org.prgrms.kdt.model.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private final static Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);

    public FixedAmountVoucher(UUID voucherId, long discountAmount, LocalDateTime createAt) {
        super(voucherId, discountAmount, createAt, VoucherType.FIXED_AMOUNT.getTypeNumber());
    }

    public FixedAmountVoucher(UUID voucherId, long discountAmount, Customer customer, LocalDateTime ownedAt, LocalDateTime createAt) {
        super(voucherId, discountAmount, createAt, customer, ownedAt, VoucherType.FIXED_AMOUNT.getTypeNumber());
    }

    @Override
    void validateDiscountAmount(long discountAmount) {
        if (discountAmount <= 0) {
            logger.info("input [discountAmount] -> {}", discountAmount);
            throw new IllegalArgumentException("discountAmount should be over 0");
        }
    }
}
