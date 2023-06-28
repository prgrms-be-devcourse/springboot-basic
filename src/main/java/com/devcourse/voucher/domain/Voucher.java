package com.devcourse.voucher.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.devcourse.voucher.domain.VoucherStatus.*;

public class Voucher {
    public final UUID id;
    private final DiscountPolicy discountPolicy;
    private final LocalDateTime expireAt;
    private VoucherStatus status;

    public Voucher(UUID id, DiscountPolicy discountPolicy, LocalDateTime expireAt, VoucherStatus status) {
        this.id = id;
        this.discountPolicy = discountPolicy;
        this.expireAt = expireAt;
        this.status = status;
    }

    public static Voucher fixed(int discountAmount, LocalDateTime expireAt) {
        DiscountPolicy fixedAmountPolicy = new FixedAmountPolicy(discountAmount);
        return new Voucher(UUID.randomUUID(), fixedAmountPolicy, expireAt, ISSUED);
    }

    public static Voucher percent(int discountRate, LocalDateTime expireAt) {
        DiscountPolicy percentDiscountPolicy = new PercentDiscountPolicy(discountRate);
        return new Voucher(UUID.randomUUID(), percentDiscountPolicy, expireAt, ISSUED);
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

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public VoucherStatus getStatus() {
        return status;
    }

    public VoucherType getType() {
        return discountPolicy.getType();
    }
}
