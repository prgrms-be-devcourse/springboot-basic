package com.example.voucher.dto;

import com.example.voucher.domain.voucher.Voucher;

import java.util.Objects;

public class VoucherResponse {
    private final Long voucherId;
    private final int discountAmount;

    public VoucherResponse(Long voucherId, int discountAmount) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    public static VoucherResponse from(Voucher voucher) {
        return new VoucherResponse(voucher.getVoucherId(), voucher.getDiscountAmount());
    }

    @Override
    public String toString() {
        return "VoucherResponse{" +
                "voucherId=" + voucherId +
                ", discountAmount=" + discountAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoucherResponse that = (VoucherResponse) o;
        return discountAmount == that.discountAmount && Objects.equals(voucherId, that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, discountAmount);
    }
}
