package com.ray.junho.voucher.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.ray.junho.voucher.domain.VoucherType.PERCENT_AMOUNT;

public class PercentDiscountVoucher extends Voucher {

    private final int discountRate;

    public PercentDiscountVoucher(long id, LocalDateTime createdAt, LocalDateTime expireAt, int discountRate)  {
        super(id, createdAt, expireAt, PERCENT_AMOUNT);

        validatePercentage(discountRate);
        this.discountRate = discountRate;
    }

    private void validatePercentage(int discountRate) {
        if (discountRate <= 0 || discountRate > 100) {
            throw new IllegalArgumentException("퍼센트는 0초과 100이하여야 합니다.");
        }
    }

    @Override
    public Currency discount(Currency beforeDiscount) {
        Currency discountedValue = beforeDiscount.multiply(Currency.of((double) discountRate / 100));
        return beforeDiscount.minus(discountedValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PercentDiscountVoucher that = (PercentDiscountVoucher) o;
        return discountRate == that.discountRate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountRate);
    }
}
