package com.example.voucher.dto;

import com.example.voucher.domain.voucher.Voucher;

import java.time.LocalDateTime;
import java.util.Objects;

public class VoucherResponse {
    private final Long voucherId;
    private final int discountAmount;
    private final LocalDateTime createdAt;

    public VoucherResponse(Long voucherId, int discountAmount, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
        this.createdAt = createdAt;
    }

    public static VoucherResponse from(Voucher voucher) {
        return new VoucherResponse(voucher.getVoucherId(), voucher.getDiscountAmount(), voucher.getCreatedAt());
    }

    @Override
    public String toString() {
        return "VoucherResponse{" +
                "voucherId=" + voucherId +
                ", discountAmount=" + discountAmount +
                ", createdAt= " + createdAt +
                '}';
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoucherResponse that = (VoucherResponse) o;
        return Objects.equals(voucherId, that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId);
    }
}
