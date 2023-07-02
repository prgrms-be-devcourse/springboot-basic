package org.prgrms.kdtspringdemo.voucher.model.entity;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);
    private static final long MIN_AMOUNT = 0;
    private static final String OUT_OF_RANGE_AMOUNT = "할인 범위가 아닙니다.";

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, VoucherType voucherType, long amount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
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
            logger.error("원인 : {} -> 에러 메시지 : {}", amount, OUT_OF_RANGE_AMOUNT);
            throw new IllegalArgumentException(OUT_OF_RANGE_AMOUNT);
        }

        return amount;
    }
}
