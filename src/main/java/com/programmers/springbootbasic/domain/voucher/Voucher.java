package com.programmers.springbootbasic.domain.voucher;

import com.programmers.springbootbasic.common.util.Validator;
import com.programmers.springbootbasic.domain.model.Duration;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
    private static final long ZERO = 0;
    private static final String USED_VOUCHER = "사용한 바우처 입니다.";
    private static final String NOT_ENOUGH_MINIMUM_PRICE_CONDITION = "최소 요구 금액을 충족하지 못합니다. 최소 요구 금액: ";
    private static final String CURRENT_PAYMENT_AMOUNT = "현재 결제 금액: ";
    private static final String EXPIRED_VOUCHER = "만료된 바우처입니다. 만료일자: ";
    private final UUID voucherId;
    private final VoucherType voucherType;
    private final String name;
    private final Long minimumPriceCondition;
    private final Duration duration;

    private boolean used;

    protected Voucher(UUID voucherId, VoucherType voucherType, String name, Long minimumPriceCondition, Duration duration, boolean used) {
        checkNullValue(voucherId, name);
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.name = name;
        this.minimumPriceCondition = minimumPriceCondition != null ? minimumPriceCondition : ZERO;
        this.duration = duration;
        this.used = used;
    }

    public static Voucher createFixedAmount(UUID voucherId, VoucherType voucherType, String name, Long minimumPriceCondition, Duration duration, int amount, boolean used) {
        return new FixedAmountVoucher(voucherId, voucherType, name, minimumPriceCondition, duration, amount, used);
    }

    public static Voucher createPercentDiscount(UUID voucherId, VoucherType voucherType, String name, Long minimumPriceCondition, Duration duration, int percent, boolean used) {
        return new PercentDiscountVoucher(voucherId, voucherType, name, minimumPriceCondition, duration, percent, used);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getName() {
        return name;
    }

    public Long getMinimumPriceCondition() {
        return minimumPriceCondition;
    }

    public Duration getDuration() {
        return duration;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public void use(Long priceBeforeDiscount, LocalDateTime at) {
        checkUndiscountable(priceBeforeDiscount, at);
        used = true;
    }

    private boolean isUsed() {
        return used;
    }

    private void checkUndiscountable(Long priceBeforeDiscount, LocalDateTime at) {
        if (isUsed()) {
            throw new IllegalStateException(USED_VOUCHER);
        }
        if (minimumPriceCondition > priceBeforeDiscount) {
            throw new IllegalStateException(NOT_ENOUGH_MINIMUM_PRICE_CONDITION + minimumPriceCondition + CURRENT_PAYMENT_AMOUNT + priceBeforeDiscount);
        }
        if (duration.isExpired(at)) {
            throw new IllegalStateException(EXPIRED_VOUCHER + duration.getExpiredAt());
        }
    }

    private void checkNullValue(UUID voucherId, String name) {
        Validator.checkNullUuid(voucherId);
        Validator.checkNullOrBlank(name);
    }

    protected abstract Long getDiscountPrice(Long priceBeforeDiscount);
}
