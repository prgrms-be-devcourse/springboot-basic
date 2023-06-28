package com.programmers.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Voucher {

    private final UUID voucherId;
    private final Discount discount;
    private final LocalDateTime createdAt;
    // 기한을 갖는 값 객체를 만들어서 필드로 갖는다.

    private static final String VOUCHER_NULL_MESSAGE = "[ERROR] 유효하지 않은 Voucher Id 입니다. (null)";

    public Voucher(UUID voucherId, Discount discount, LocalDateTime createdAt) {
        validateVoucherId(voucherId);
        this.voucherId = voucherId;
        this.discount = discount;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    private void validateVoucherId(UUID voucherId) {
        if (voucherId == null) throw new IllegalArgumentException(VOUCHER_NULL_MESSAGE);
    }
}
