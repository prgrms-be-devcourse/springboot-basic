package com.programmers.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherEntity {

    private final UUID voucherId;
    private final Discount discount;
    private final LocalDateTime createdDate;

    public VoucherEntity(UUID voucherId, Discount discount, LocalDateTime createdDate) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.createdDate = createdDate;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Discount getDiscount() {
        return discount;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}
