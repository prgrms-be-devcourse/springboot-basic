package com.programmers.voucher.domain.voucher;

import org.springframework.stereotype.Component;

@Component
public class FixedAmountVoucher implements Voucher {
    private String voucherId;
    private long amount;

    public FixedAmountVoucher() {
    }

    public FixedAmountVoucher(String voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public String getVoucherId() {
        return this.voucherId;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public long discount(long originalPrice) {
        long discountedPrice = originalPrice - amount;
        if (discountedPrice < 0) {
            throw new IllegalStateException("할인 금액이 물건 가격보다 더 큽니다.");
        }
        return discountedPrice;
    }

    @Override
    public void update(long updateAmount) {
        validateUpdateAmount(updateAmount);
        this.amount = updateAmount;
    }

    @Override
    public long getDiscount() {
        return this.amount;
    }

    @Override
    public String getType() {
        return "FixedAmountVoucher";
    }

    private void validateUpdateAmount(long updateAmount) {
        if (updateAmount <= 0) {
            throw new IllegalArgumentException("할인금액은 0보다 작거나 같을 수 없습니다.");
        }
    }

}
