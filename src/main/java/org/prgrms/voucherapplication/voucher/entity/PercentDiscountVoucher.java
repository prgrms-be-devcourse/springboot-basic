package org.prgrms.voucherapplication.voucher.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher{

    private final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);
    private static final String PERCENT_MAX = "할인 퍼센트는 100% 이하만 가능합니다.";

    public PercentDiscountVoucher(UUID uuid, int percent, LocalDateTime createdAt) {
        super(uuid, percent, VoucherType.PERCENT, createdAt);

        final int MAX_PERCENT = 100;
        if (percent > MAX_PERCENT) {
            logger.error(PERCENT_MAX);
            throw new VoucherConstructorException(PERCENT_MAX);
        }
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "uuid=" + voucherId +
                ", discount=" + discount +
                ", voucherType=" + voucherType +
                ", createdAt=" + createdAt +
                '}';
    }
}
