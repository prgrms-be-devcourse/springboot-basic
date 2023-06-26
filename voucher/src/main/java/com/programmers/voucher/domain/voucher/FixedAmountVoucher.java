package com.programmers.voucher.domain.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class FixedAmountVoucher implements Voucher {
    private String voucherId;
    private long amount;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
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
        long discountedPrice = originalPrice - amount;
        if (discountedPrice < 0) {
            log.info("할인 오류, 할인 금액이 물건 가격보다 큽니다. 할인 금액 : {}, 물건 가격: {}", this.amount, originalPrice);
            throw new IllegalStateException("할인 금액이 물건 가격보다 더 큽니다.");
        }
        return discountedPrice;
    }
}
