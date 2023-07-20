package org.prgrms.kdtspringdemo.voucher.model.entity;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.prgrms.kdtspringdemo.voucher.exception.VoucherExceptionMessage.OUT_OF_RANGE_AMOUNT;

public class FixedAmountVoucher implements Voucher {
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);
    private static final long MIN_AMOUNT = 0;

    private final UUID id;
    private final VoucherType type;
    private final long amount;

    public FixedAmountVoucher(long amount) {
        this.id = UUID.randomUUID();
        this.type = VoucherType.FIXED;
        this.amount = validateAmount(amount);
    }

    public FixedAmountVoucher(UUID id, long amount) {
        this.id = id;
        this.type = VoucherType.FIXED;
        this.amount = validateAmount(amount);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public VoucherType getType() {
        return type;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long executeDiscount(long originPrice) {
        return Math.max(0L, originPrice - amount);
    }

    @Override
    public long validateAmount(long amount) {
        if (amount <= MIN_AMOUNT) {
            logger.warn("원인 : {} -> 에러 메시지 : {}", amount, OUT_OF_RANGE_AMOUNT.getMessage());
            throw new IllegalArgumentException(OUT_OF_RANGE_AMOUNT.getMessage());
        }

        return amount;
    }
}
