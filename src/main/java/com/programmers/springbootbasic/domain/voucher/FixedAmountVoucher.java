package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final int amount;
    static final int MIN_AMOUNT = 10;
    static final int MAX_AMOUNT = 10_000_000;

    public FixedAmountVoucher(UUID voucherId, String name, LocalDateTime createdDate, LocalDateTime expirationDate, int amount) {
        this(voucherId, name, 0L, createdDate, expirationDate, amount);
    }

    public FixedAmountVoucher(UUID voucherId, String name, Long minimumPriceCondition, LocalDateTime createdDate, LocalDateTime expirationDate, int amount) {
        super(voucherId, name, minimumPriceCondition, createdDate, expirationDate);
        if (isInvalidAmount(amount)) {
            throw new IllegalArgumentException("잘못된 할인 금액입니다. " +
                    "할인 금액은 최소 " + MIN_AMOUNT + "원부터 최대 " + MAX_AMOUNT + "원까지 설정 가능합니다. " +
                    "현재 입력 금액: " + amount + "원"
            );
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

    private boolean isInvalidAmount(int amount) {
        return amount < MIN_AMOUNT || MAX_AMOUNT < amount;
    }
}
