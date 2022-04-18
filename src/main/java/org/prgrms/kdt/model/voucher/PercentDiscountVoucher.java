package org.prgrms.kdt.model.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private UUID voucherId;
    private long discountPercent;
    private static final int MAX_DISCOUNT_PERCENT = 100;
    private final static Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);

    public PercentDiscountVoucher(UUID voucherId, long discountPercent) {
        validateDiscountPercent(discountPercent);
        this.voucherId = voucherId;
        this.discountPercent = discountPercent;
    }

    @Override
    public UUID getVoucherId() {
        return null;
    }

    @Override
    public long getDiscountAmount() {
        return discountPercent;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", discountPercent=" + discountPercent +
                '}';
    }

    private void validateDiscountPercent(long discountPercent) {
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
