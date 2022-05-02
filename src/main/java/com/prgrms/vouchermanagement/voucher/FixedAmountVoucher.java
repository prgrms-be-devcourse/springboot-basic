package com.prgrms.vouchermanagement.voucher;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher, Serializable {

    private final UUID voucherId;
    private final long discountPrice;
    private final LocalDateTime createdAt;

    public FixedAmountVoucher(UUID voucherId, long discountPrice, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.discountPrice = discountPrice;
        this.createdAt = createdAt;
    }

    public static Voucher of(UUID voucherId, long discountPrice, LocalDateTime createdAt) throws IllegalArgumentException {
        if (discountPrice < 0){
            throw new IllegalArgumentException("0보다 작은 값은 입력할 수 없습니다.");
        }
        return new FixedAmountVoucher(voucherId, discountPrice, createdAt);
    }

    @Override
    public long discount(long beforeDiscountPrice) {
        return beforeDiscountPrice - discountPrice;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return discountPrice;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Id=" + getVoucherId() + ", Type=FixedAmount, discountPrice="+ discountPrice +"won";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedAmountVoucher that = (FixedAmountVoucher) o;
        return discountPrice == that.discountPrice && Objects.equals(voucherId, that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, discountPrice);
    }
}
