package com.prgrms.vouchermanagement.voucher;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class FixedAmountVoucher implements Voucher, Serializable {

    private final Long voucherId;
    private final long discountPrice;
    private final LocalDateTime createdAt;

    public FixedAmountVoucher(Long voucherId, long discountPrice, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.discountPrice = discountPrice;
        this.createdAt = createdAt;
    }

    public static Voucher of(Long voucherId, long discountPrice, LocalDateTime createdAt) throws IllegalArgumentException {
        if (discountPrice < 0){
            throw new IllegalArgumentException("0보다 작은 값은 입력할 수 없습니다.");
        }
        return new FixedAmountVoucher(voucherId, discountPrice, createdAt);
    }

    public static Voucher of(long discountPrice, LocalDateTime createdAt) throws IllegalArgumentException {
        return FixedAmountVoucher.of(null, discountPrice, createdAt);
    }

    @Override
    public long discount(long beforeDiscountPrice) {
        return beforeDiscountPrice - discountPrice;
    }

    @Override
    public Long getVoucherId() {
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
