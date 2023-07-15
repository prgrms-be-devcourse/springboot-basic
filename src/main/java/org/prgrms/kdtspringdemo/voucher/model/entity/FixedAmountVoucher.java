package org.prgrms.kdtspringdemo.voucher.model.entity;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.prgrms.kdtspringdemo.voucher.exception.VoucherExceptionMessage.OUT_OF_RANGE_AMOUNT;

public class FixedAmountVoucher implements Voucher {
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);
    private static final long MIN_AMOUNT = 0;

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final long amount;

    public FixedAmountVoucher(long amount) {
        this.voucherId = UUID.randomUUID();
        this.voucherType = VoucherType.FIXED;
        this.amount = validateAmount(amount);
    }

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.voucherType = VoucherType.FIXED;
        this.amount = validateAmount(amount);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
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
