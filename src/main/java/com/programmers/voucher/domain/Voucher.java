package com.programmers.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Voucher {

    private final UUID voucherId;
    private final Discount discount;
    private final LocalDateTime createdDate;

    public Voucher(UUID voucherId, Discount discount, LocalDateTime createdDate) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.createdDate = createdDate;
    }

    public long applyDiscount(long itemPrice) {
        return discount.applyDiscount(itemPrice);
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
