package com.programmers.voucher.domain.voucher;

import org.springframework.stereotype.Component;

@Component
public class PercentDiscountVoucher implements Voucher{
    private String voucherId;
    private long percent;

    public PercentDiscountVoucher() {
    }

    public PercentDiscountVoucher(String voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    public long getPercent() {
        return percent;
    }

    @Override
    public String getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long discount(long originalPrice) {
        return originalPrice * (percent / 100);
    }

}
