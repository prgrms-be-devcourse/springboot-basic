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
}
