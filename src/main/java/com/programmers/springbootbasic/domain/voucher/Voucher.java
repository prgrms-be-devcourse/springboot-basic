package com.programmers.springbootbasic.domain.voucher;

import com.programmers.springbootbasic.common.util.Validator;

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

    private boolean used = false;

    protected Voucher(UUID voucherId, VoucherType voucherType, String name, Long minimumPriceCondition, Duration duration) {
        /*
        null 체크의 기준을 잘 모르겠습니다.
        VoucherType 과 VoucherDateTime 도 null 체크를 해줘야 될까요?
        voucherId 는 생성 시에 UUID.randomUUID() 생성해서 바로 대입하기 때문에
        null 값이 들어올 일이 없다고 생각했는데 멘토님들의 피드백으로 null 체크를 했습니다.
        위와 같은 맥락이면 voucherType, voucherDateTime 도 해줘야 한다고 생각하는데 해주는게 맞을까요??
        또, null 체크에 대한 기준을 듣고 싶습니다!
        */
        checkNullValue(voucherId, name);
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.name = name;
        this.minimumPriceCondition = minimumPriceCondition != null ? minimumPriceCondition : ZERO;
        this.duration = duration;
    }

    public static Voucher createFixedAmount(UUID voucherId, VoucherType voucherType, String name, Long minimumPriceCondition, Duration duration, int amount) {
        return new FixedAmountVoucher(voucherId, voucherType, name, minimumPriceCondition, duration, amount);
    }

    public static Voucher createPercentDiscount(UUID voucherId, VoucherType voucherType, String name, Long minimumPriceCondition, Duration duration, int percent) {
        return new PercentDiscountVoucher(voucherId, voucherType, name, minimumPriceCondition, duration, percent);
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

    public Duration getVoucherDate() {
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
