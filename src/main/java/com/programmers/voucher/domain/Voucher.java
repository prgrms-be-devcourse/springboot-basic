package com.programmers.voucher.domain;

import com.programmers.global.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.UUID;

public class Voucher {

    private final UUID voucherId;
    private final Discount discount;
    private final LocalDateTime createdDate;

    private static final String VOUCHER_NULL_MESSAGE = "[ERROR] Voucher ID를 찾을 수 없습니다.";

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
        if (voucherId == null) throw new NotFoundException(VOUCHER_NULL_MESSAGE);
    }
}
