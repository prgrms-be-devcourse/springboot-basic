package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final Long MIN_AMOUNT = 10L;
    private final Long MAX_AMOUNT = 10_000_000L;
    private final Long amount;

    public FixedAmountVoucher(UUID voucherId, String name, LocalDateTime expirationDate, Long amount) {
        super(voucherId, name, expirationDate);
        if (isInvalidateAmount(amount)) {
            throw new IllegalArgumentException("잘못된 할인 금액");
        }
        this.amount = amount;
    }

    public FixedAmountVoucher(UUID voucherId, String name, Long minimumPriceCondition, LocalDateTime expirationDate, Long amount) {
        super(voucherId, name, minimumPriceCondition, expirationDate);
        if (isInvalidateAmount(amount)) {
            throw new IllegalArgumentException("잘못된 할인 금액");
        }
        this.amount = amount;
    }

    @Override
    public Long discount(Long priceBeforeDiscount) {
        if (priceBeforeDiscount <= amount) {
            return 0L;
        }
        return priceBeforeDiscount - amount;
    }

    private Boolean isInvalidateAmount(Long amount) {
        return amount < MIN_AMOUNT || MAX_AMOUNT < amount;
    }
}
