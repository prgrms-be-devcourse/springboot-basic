package com.programmers.voucher.domain.voucher;

import org.springframework.stereotype.Component;

@Component
public class PercentDiscountVoucher implements Voucher{
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

    @Override
    public String getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long discount(long originalPrice) {
        return originalPrice * (rate / 100);
    }

}
