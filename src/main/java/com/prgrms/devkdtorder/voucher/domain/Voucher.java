package com.prgrms.devkdtorder.voucher.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class Voucher implements Discountable {

    protected final UUID voucherId;
    protected final long value;
    protected final String name;
    protected final LocalDateTime createdAt;

    public Voucher(UUID voucherId, long value, String name, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.value = value;
        this.name = name;
        this.createdAt = createdAt;
    }
}
