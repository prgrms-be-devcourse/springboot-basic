package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final int amount;

    public FixedAmountVoucher(UUID voucherId, String name, LocalDateTime expirationDate, int amount) {
        super(voucherId, name, expirationDate);
        if (isInvalidateAmount(amount)) {
            throw new IllegalArgumentException("잘못된 할인 금액");
        }
        this.amount = amount;
    }

    public FixedAmountVoucher(UUID voucherId, String name, Long minimumPriceCondition, LocalDateTime expirationDate, int amount) {
        super(voucherId, name, minimumPriceCondition, expirationDate);
        if (isInvalidateAmount(amount)) {
            throw new IllegalArgumentException("잘못된 할인 금액");
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

    private Boolean isInvalidateAmount(int amount) {
        int MIN_AMOUNT = 10;
        int MAX_AMOUNT = 10_000_000;
        return amount < MIN_AMOUNT || MAX_AMOUNT < amount;
    }
}
