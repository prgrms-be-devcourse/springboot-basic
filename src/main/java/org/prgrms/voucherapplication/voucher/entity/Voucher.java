package org.prgrms.voucherapplication.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {

    protected final UUID voucherId;
    protected final int discount;
    protected final VoucherType voucherType;
    protected final LocalDateTime createdAt;

    protected Voucher(UUID voucherId, int discount, VoucherType voucherType, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public int getDiscount() {
        return discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
