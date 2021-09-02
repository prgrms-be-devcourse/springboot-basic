package com.prgrms.devkdtorder.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class AbstractVoucher implements Voucher {

    protected final UUID voucherId;
    protected final long value;
    protected final String name;
    protected final LocalDateTime createdAt;

    public AbstractVoucher(UUID voucherId, long value, String name, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.value = value;
        this.name = name;
        this.createdAt = createdAt;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
