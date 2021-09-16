package org.prgrms.kdt.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);
    private static final long MAX_VOUCHER_PERCENT = 100;

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent < 0) {
            String errorMsg = "Percent should be positive";
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        if (percent == 0) {
            String errorMsg = "Percent should not be zero";
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        if (percent > MAX_VOUCHER_PERCENT) {
            String errorMsg = String.format("Percent should be less than %d", MAX_VOUCHER_PERCENT);
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        this.voucherId = voucherId;
        this.percent = percent;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - beforeDiscount * percent / MAX_VOUCHER_PERCENT;
        if (discountedAmount < 0) {
            return 0;
        }
        return discountedAmount;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher" +
                "," + voucherId +
                "," + percent;
    }
}