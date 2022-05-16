package com.programmers.springbootbasic.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Voucher {

    private final UUID voucherId;
    private LocalDateTime createdAt;
    private final Long fixedAmount;
    private final Integer discountPercent;
    private final Integer type;

    public Voucher(UUID voucherId, Long fixed_amount, Integer discount_percent, Integer type) {
        this.voucherId = voucherId;
        this.fixedAmount = fixed_amount;
        this.discountPercent = discount_percent;
        this.type = type;
    }

    public Voucher(UUID voucherId, LocalDateTime createdAt, Long fixed_amount, Integer discount_percent, Integer type) {
        this.voucherId = voucherId;
        this.createdAt = createdAt;
        this.fixedAmount = fixed_amount;
        this.discountPercent = discount_percent;
        this.type = type;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getFixedAmount() {
        return fixedAmount;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public Integer getType() {
        return type;
    }

    @Override
    public String toString() {
        return  "[할인권 ID: " + voucherId + "], [생성날짜: " + createdAt + "], [고정금액: " + fixedAmount +
                "], [할인율: " + discountPercent +
                "], [할인권종류: " + type + "]";
    }

}
