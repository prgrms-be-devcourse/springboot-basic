package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final Long amount;

    public FixedAmountVoucher(UUID voucherId, String name, LocalDateTime expirationDate, Long amount) {
        super(voucherId, name, expirationDate);
        this.amount = amount;
    }

    public FixedAmountVoucher(UUID voucherId, String name, Long condition, LocalDateTime expirationDate, Long amount) {
        super(voucherId, name, condition, expirationDate);
        this.amount = amount;
    }

    @Override
    public Long discount(Long priceBeforeDiscount) {
        if (priceBeforeDiscount - amount <= 0) {
            return 0L;
        }
        return priceBeforeDiscount - amount;
    }

    private boolean
}
