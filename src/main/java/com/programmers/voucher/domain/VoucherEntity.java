package com.programmers.voucher.domain;

import java.time.LocalDate;
import java.util.UUID;

public class VoucherEntity {

    private final UUID voucherId;
    private final Discount discount;
    private final LocalDate createdDate;

    public VoucherEntity(UUID voucherId, Discount discount, LocalDate createdDate) {
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

    public LocalDate getCreatedDate() {
        return createdDate;
    }
}
