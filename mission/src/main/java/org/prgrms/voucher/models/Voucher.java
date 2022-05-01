package org.prgrms.voucher.models;

import java.time.LocalDateTime;

public abstract class Voucher {

    private final Long voucherId;
    private final long discountValue;
    private final VoucherType voucherType;
    private final LocalDateTime createdAt;

    protected Voucher(long discountValue, VoucherType voucherType) {

        this.voucherId = null;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
        this.createdAt = LocalDateTime.now();
    }

    protected Voucher(Long voucherId, long discountValue, VoucherType voucherType, LocalDateTime createdAt) {

        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }

    public Long getVoucherId() {

        return voucherId;
    }

    public long getDiscountValue() {

        return discountValue;
    }

    public VoucherType getVoucherType() {

        return voucherType;
    }

    public LocalDateTime getCreatedAt() {

        return createdAt;
    }

    abstract public long discount();
}
