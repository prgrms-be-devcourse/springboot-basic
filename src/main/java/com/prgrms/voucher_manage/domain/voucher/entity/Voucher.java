package com.prgrms.voucher_manage.domain.voucher.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class Voucher {
    private final UUID id;
    private final Long discountAmount;
    private final VoucherType type;
    private final LocalDateTime createdAt;

    public Voucher(UUID id, Long discountAmount, VoucherType type) {
        this.id = id;
        this.discountAmount = discountAmount;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }
}
