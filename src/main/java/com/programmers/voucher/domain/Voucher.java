package com.programmers.voucher.domain;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

public class Voucher {

    private final UUID voucherId;
    private final Discount discount;
    private final LocalDateTime createdDate;

    private static final String VOUCHER_NULL_MESSAGE = "[ERROR] 유효하지 않은 Voucher Id 입니다. (null)";

    public Voucher(UUID voucherId, Discount discount, LocalDateTime createdDate) {
        validateVoucherId(voucherId);
        this.voucherId = voucherId;
        this.discount = discount;
        this.createdDate = createdDate;
    }

    public long discount(long itemPrice) {
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

    private void validateVoucherId(UUID voucherId) {
        if (voucherId == null) throw new IllegalArgumentException(VOUCHER_NULL_MESSAGE);
    }
}
