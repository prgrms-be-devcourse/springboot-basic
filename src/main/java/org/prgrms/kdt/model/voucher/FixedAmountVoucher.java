package org.prgrms.kdt.model.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private UUID voucherId;
    private long discountAmount;
    private final static Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);

    public FixedAmountVoucher(UUID voucherId, long discountAmount) {
        validateDiscountAmount(discountAmount);
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long getDiscountAmount() {
        return this.discountAmount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher { " +
                "voucherId = " + voucherId +
                ", discountAmount = " + discountAmount +
                " }";
    }

    private void validateDiscountAmount(long discountAmount) {
        if (discountAmount <= 0) {
            logger.info("input [discountAmount] -> {}", discountAmount);
            throw new IllegalArgumentException("discountAmount should be over 0");
        }
    }
}
