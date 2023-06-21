package com.programmers.voucher.domain.voucher;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
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

    @Override
    public long discount(long originalPrice) {
        return originalPrice - amount;
    }
}
