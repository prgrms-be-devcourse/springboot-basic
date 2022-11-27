package org.prgrms.voucherapplication.voucher.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher{

    private final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);
    private static final String DISCOUNT_MIN = "할인금액은 0 이상만 가능합니다.";

    public FixedAmountVoucher(UUID uuid, int discount, LocalDateTime createdAt) {
        super(uuid, discount, VoucherType.FIXED, createdAt);

        final int  MIN = 0;
        if (discount < MIN) {
            logger.error(DISCOUNT_MIN);
            throw new VoucherConstructorException(DISCOUNT_MIN);
        }
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "uuid=" + voucherId +
                ", discount=" + discount +
                ", voucherType=" + voucherType +
                ", createdAt=" + createdAt +
                '}';
    }
}
