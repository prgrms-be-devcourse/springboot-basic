package org.prgrms.assignment.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final int MIN_VOUCHER_PERCENT = 0;
    private static final long MAX_VOUCHER_PERCENT = 100;

    private final UUID voucherId;
    private final LocalDateTime createdAt;
    private long percent;
    private LocalDateTime expireDate;

    public PercentDiscountVoucher(UUID voucherId, long percent, LocalDateTime createdAt, LocalDateTime expireDate) {
        checkValid(percent);
        this.voucherId = voucherId;
        this.percent = percent;
        this.createdAt = createdAt;
        this.expireDate = expireDate;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public long getBenefit() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public void setBenefit(long benefit) {
        this.percent = benefit;
    }

    @Override
    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    private void checkValid(long percent) {
        if(percent < MIN_VOUCHER_PERCENT || percent >= MAX_VOUCHER_PERCENT) {
            throw new IllegalArgumentException("percent should be higher than 0 or lower than 100");
        }
    }
}
