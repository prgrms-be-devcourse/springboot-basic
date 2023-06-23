package org.promgrammers.springbootbasic.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private static final long MIN_AMOUNT = 0;
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);

    public FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId, amount);
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountAmount = beforeDiscount - this.getAmount();
        return discountAmount < 0 ? 0 : discountAmount;
    }

    @Override
    protected void validateAmount(long discountAmount) {
        if (discountAmount < MIN_AMOUNT) {
            logger.error("Invalid Discount Amount => {}", discountAmount);
            throw new IllegalArgumentException("할인 금액은 0보다 적을 수 없습니다.");
        }
    }
}
