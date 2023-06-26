package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final int amount;
    static int MIN_AMOUNT = 10;
    static int MAX_AMOUNT = 10_000_000;

    public FixedAmountVoucher(UUID voucherId, String name, LocalDateTime expirationDate, int amount) {
        this(voucherId, name, 0L, expirationDate, amount);
    }

    public FixedAmountVoucher(UUID voucherId, String name, Long minimumPriceCondition, LocalDateTime expirationDate, int amount) {
        super(voucherId, name, minimumPriceCondition, expirationDate);
        if (isInvalidAmount(amount)) {
            throw new IllegalArgumentException("잘못된 할인 금액, amount=" + amount);
        }
        this.amount = amount;
    }

    @Override
    protected Long getDiscountPrice(Long priceBeforeDiscount) {
        if (priceBeforeDiscount <= amount) {
            return 0L;
        }
        return priceBeforeDiscount - amount;
    }

    @Override
    public Long discount(Long priceBeforeDiscount) {
        setVoucherState(VoucherState.USED);
        return getDiscountPrice(priceBeforeDiscount);
    }

    private boolean isInvalidAmount(int amount) {
        return amount < MIN_AMOUNT || MAX_AMOUNT < amount;
    }
}
