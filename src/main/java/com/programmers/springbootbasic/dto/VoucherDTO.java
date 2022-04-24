package com.programmers.springbootbasic.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDTO {

    private UUID voucherId;
    private LocalDateTime createdAt;
    private Long fixedAmount;
    private Integer discountPercent;
    private Integer type;

    public VoucherDTO(UUID voucherId, Long fixed_amount, Integer discount_percent, Integer type) {
        this.voucherId = voucherId;
        this.fixedAmount = fixed_amount;
        this.discountPercent = discount_percent;
        this.type = type;
    }

    public VoucherDTO(UUID voucherId, LocalDateTime createdAt, Long fixed_amount, Integer discount_percent, Integer type) {
        this.voucherId = voucherId;
        this.createdAt = createdAt;
        this.fixedAmount = fixed_amount;
        this.discountPercent = discount_percent;
        this.type = type;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getFixedAmount() {
        return fixedAmount;
    }

    public void setFixedAmount(Long fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return  "[할인권 ID: " + voucherId + "], [생성날짜: " + createdAt + "], [고정금액: " + fixedAmount +
                "], [할인율: " + discountPercent +
                "], [할인권종류: " + type + "]";
    }

}
