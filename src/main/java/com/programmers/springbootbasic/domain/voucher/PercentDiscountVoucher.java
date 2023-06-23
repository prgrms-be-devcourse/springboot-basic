package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final int percent;

    public PercentDiscountVoucher(UUID voucherId, String name, LocalDateTime expirationDate, int percent) {
        super(voucherId, name, expirationDate);
        this.percent = percent;
    }

    public PercentDiscountVoucher(UUID voucherId, String name, Long minimumPrice, LocalDateTime expirationDate, int percent) {
        super(voucherId, name, minimumPrice, expirationDate);
        this.percent = percent;
    }

    @Override
    public Long discount(Long priceBeforeDiscount) {
        return Math.round(priceBeforeDiscount * ((100 - percent) / 100d) / 10) * 10;
    }
}