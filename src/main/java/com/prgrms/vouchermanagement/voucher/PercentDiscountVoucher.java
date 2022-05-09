package com.prgrms.vouchermanagement.voucher;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class PercentDiscountVoucher implements Voucher, Serializable {

    private Long voucherId;
    private long discountPercentage;
    private LocalDateTime createdAt;

    public PercentDiscountVoucher(Long voucherId, long discountPercentage, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.discountPercentage = discountPercentage;
        this.createdAt = createdAt;
    }

    public static Voucher of(Long voucherId, long discountPercentage, LocalDateTime createdAt) throws IllegalArgumentException {
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Percent discount 는 0 ~ 100% 범위의 값을 입력해주세요.");
        }
        return new PercentDiscountVoucher(voucherId, discountPercentage, createdAt);
    }

    public static Voucher of(long discountPercentage, LocalDateTime createdAt) throws IllegalArgumentException {
        return PercentDiscountVoucher.of(null, discountPercentage, createdAt);
    }

    @Override
    public long discount(long beforeDiscountPrice) {
        return beforeDiscountPrice * (discountPercentage / 100);
    }

    @Override
    public Long getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return discountPercentage;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Id=" + getVoucherId() + ", Type=PercentDiscount, discountPercent=" + discountPercentage + "%";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PercentDiscountVoucher that = (PercentDiscountVoucher) o;
        return discountPercentage == that.discountPercentage && Objects.equals(voucherId, that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, discountPercentage);
    }
}
