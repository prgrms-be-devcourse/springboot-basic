package com.programmers.voucher.domain.voucher;

import org.springframework.stereotype.Component;

@Component
public class PercentDiscountVoucher implements Voucher {
    private String voucherId;
    private long rate;

    public PercentDiscountVoucher() {
    }

    public PercentDiscountVoucher(String voucherId, long rate) {
        this.voucherId = voucherId;
        this.rate = rate;
    }

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        validateRate(rate);
        this.rate = rate;
    }

    @Override
    public String getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long discount(long originalPrice) {
        return originalPrice * (rate / 100);
    }

    @Override
    public void update(long updateAmount) {
        validateRate(updateAmount);
        this.rate = updateAmount;
    }

    @Override
    public long getDiscount() {
        return this.rate;
    }

    @Override
    public String getType() {
        return "PercentDiscountVoucher";
    }

    private void validateRate(long rate) {
        if (rate < 0 || rate >= 100) {
            throw new IllegalArgumentException("할인률은 0 초과 100미만 이어야합니다.");
        }
    }

}
