package com.devcourse.voucher.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.devcourse.voucher.domain.Voucher.Status.ISSUED;
import static com.devcourse.voucher.domain.Voucher.Status.USED;

public class Voucher {
    protected enum Status { USED, ISSUED }
    public enum Type {
        FIXED, PERCENT;

        public boolean isPercent() {
            return this == PERCENT;
        }
    }

    private final UUID id;
    private final DiscountPolicy discountPolicy;
    private final int discount;
    private final LocalDateTime expireAt;
    private final Type type;
    private Status status;

    Voucher(int discount, LocalDateTime expireAt, Type type) {
        this.id = UUID.randomUUID();
        this.discountPolicy = createPolicy(type);
        this.discount = discount;
        this.expireAt = expireAt;
        this.type = type;
        this.status = ISSUED;
    }

    public static Voucher of(int discount, LocalDateTime expireAt, Type type) {
        return new Voucher(discount, expireAt, type);
    }

    public BigDecimal apply(long price) {
        this.status = USED;
        return discountPolicy.discount(price, discount);
    }

    public boolean isUsed() {
        return this.status == USED;
    }

    public UUID getId() {
        return id;
    }

    public int getDiscount() {
        return this.discount;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public String getStatus() {
        return status.name();
    }

    public Type getType() {
        return type;
    }

    private DiscountPolicy createPolicy(Type type) {
        if (type.isPercent()) {
            return new PercentDiscountPolicy();
        }

        return new FixedAmountPolicy();
    }
}
