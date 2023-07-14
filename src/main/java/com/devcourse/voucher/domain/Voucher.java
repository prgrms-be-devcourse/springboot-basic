package com.devcourse.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.devcourse.global.common.Constant.DELIMITER;
import static com.devcourse.voucher.domain.Voucher.Status.ISSUED;
import static com.devcourse.voucher.domain.Voucher.Status.USED;

public class Voucher {
    public enum Status { USED, ISSUED }
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

    public Voucher(UUID id, int discount, LocalDateTime expireAt, Type type, Status status) {
        this.id = id;
        this.discountPolicy = createPolicy(type);
        this.discount = discount;
        this.expireAt = expireAt;
        this.type = type;
        this.status = status;
    }

    public Voucher(int discount, LocalDateTime expireAt, Type type) {
        this.id = UUID.randomUUID();
        this.discountPolicy = createPolicy(type);
        this.discount = discount;
        this.expireAt = expireAt;
        this.type = type;
        this.status = ISSUED;
    }

    public boolean isUsed() {
        return this.status == USED;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public String toText() {
        return id + DELIMITER +
                discount + DELIMITER +
                type + DELIMITER +
                expireAt + DELIMITER +
                status;
    }

    private DiscountPolicy createPolicy(Type type) {
        if (type.isPercent()) {
            return new PercentDiscountPolicy();
        }

        return new FixedAmountPolicy();
    }
}
