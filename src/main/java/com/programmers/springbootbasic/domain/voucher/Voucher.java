package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
    private static final long ZERO = 0;
    private static final String USED_VOUCHER = "사용한 바우처 입니다.";
    private static final String NOT_ENOUGH_MINIMUM_PRICE_CONDITION = "최소 요구 금액을 충족하지 못합니다. 최소 요구 금액: ";
    private static final String CURRENT_PAYMENT_AMOUNT = "현재 결제 금액: ";
    private static final String EXPIRED_VOUCHER = "만료된 바우처입니다. 만료일자: ";
    private static final String ID_IS_NULL = "빈 값이나 공백을 아이디로 사용할 수 없습니다. 현재 값: ";
    private static final String NAME_IS_BLANK = "빈 값이나 공백을 이름으로 사용할 수 없습니다. 현재 값: ";
    private final UUID voucherId;
    private final String name;
    private final Long minimumPriceCondition;
    private final VoucherDateTime voucherDateTime;

    private boolean used = false;

    protected Voucher(UUID voucherId, String name, Long minimumPriceCondition, VoucherDateTime voucherDateTime) {
        checkNullValue(voucherId, name);
        this.voucherId = voucherId;
        this.name = name;
        this.minimumPriceCondition = minimumPriceCondition == null ? ZERO : minimumPriceCondition;
        this.voucherDateTime = voucherDateTime;
    }

    public static Voucher createFixedAmount(UUID voucherId, String name, Long minimumPriceCondition, VoucherDateTime voucherDateTime, int amount) {
        return new FixedAmountVoucher(voucherId, name, minimumPriceCondition, voucherDateTime, amount);
    }

    public static Voucher createPercentDiscount(UUID voucherId, String name, Long minimumPriceCondition, VoucherDateTime voucherDateTime, int percent) {
        return new PercentDiscountVoucher(voucherId, name, minimumPriceCondition, voucherDateTime, percent);
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

    public VoucherDateTime getVoucherDate() {
        return voucherDateTime;
    }

    public void use(Long priceBeforeDiscount, LocalDateTime at) {
        checkUndiscountable(priceBeforeDiscount, at);
        used = true;
    }

    private boolean isUsed() {
        return used;
    }

    private boolean isNullVoucherId(UUID voucherId) {
        return voucherId == null;
    }

    private boolean isNullName(String name) {
        return name == null || name.isBlank();
    }

    private void checkUndiscountable(Long priceBeforeDiscount, LocalDateTime at) {
        if (isUsed()) {
            throw new IllegalStateException(USED_VOUCHER);
        }
        if (minimumPriceCondition > priceBeforeDiscount) {
            throw new IllegalStateException(NOT_ENOUGH_MINIMUM_PRICE_CONDITION + minimumPriceCondition + CURRENT_PAYMENT_AMOUNT + priceBeforeDiscount);
        }
        if (voucherDateTime.isExpired(at)) {
            throw new IllegalStateException(EXPIRED_VOUCHER + voucherDateTime.getExpiredAt());
        }
    }

    private void checkNullValue(UUID voucherId, String name) {
        if (isNullVoucherId(voucherId)) {
            throw new IllegalArgumentException(ID_IS_NULL + voucherId);
        }
        if (isNullName(name)) {
            throw new IllegalArgumentException(NAME_IS_BLANK + name);
        }
    }

    protected abstract Long getDiscountPrice(Long priceBeforeDiscount);
}
