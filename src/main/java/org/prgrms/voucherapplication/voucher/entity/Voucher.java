package org.prgrms.voucherapplication.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {

    protected final UUID uuid;
    protected final int discount;
    protected final VoucherType voucherType;
    protected final LocalDateTime createdAt;

    protected Voucher(UUID uuid, int discount, VoucherType voucherType, LocalDateTime createdAt) {
        this.uuid = uuid;
        this.discount = discount;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }

    public UUID getUuid() {
        return uuid;
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
