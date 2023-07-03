package com.devcourse.voucher.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.devcourse.voucher.domain.Voucher.Status.*;

public class Voucher {
    protected enum Status { USED, ISSUED }

    private final UUID id;
    private final DiscountPolicy discountPolicy;
    private final LocalDateTime expireAt;
    private Status status;

    public Voucher(UUID id, DiscountPolicy discountPolicy, LocalDateTime expireAt) {
        this.id = id;
        this.discountPolicy = discountPolicy;
        this.expireAt = expireAt;
        this.status = ISSUED;
    }

    public static Voucher fixed(int discountAmount, LocalDateTime expireAt) {
        DiscountPolicy fixedAmountPolicy = new FixedAmountPolicy(discountAmount);
        return new Voucher(UUID.randomUUID(), fixedAmountPolicy, expireAt);
    }

    public static Voucher percent(int discountRate, LocalDateTime expireAt) {
        DiscountPolicy percentDiscountPolicy = new PercentDiscountPolicy(discountRate);
        return new Voucher(UUID.randomUUID(), percentDiscountPolicy, expireAt);
    }

    public BigDecimal apply(long price) {
        this.status = USED;
        return discountPolicy.discount(price);
    }

    public boolean isUsed() {
        return this.status == USED;
    }

    public UUID getId() {
        return id;
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    public BigDecimal getDiscount() {
        return discountPolicy.discountAmount();
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public String getStatus() {
        return status.name();
    }

    public VoucherType getType() {
        return discountPolicy.getType();
    }
}
