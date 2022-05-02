package com.mountain.voucherApp.model;

import com.mountain.voucherApp.model.enums.DiscountPolicy;

import java.text.MessageFormat;
import java.util.UUID;

public class VoucherEntity {
    private final UUID voucherId;
    private DiscountPolicy discountPolicy;
    private Long discountAmount;

    public VoucherEntity(UUID voucherId, DiscountPolicy discountPolicy, Long discountAmount) {
        this.voucherId = voucherId;
        this.discountPolicy = discountPolicy;
        this.discountAmount = discountAmount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    public void changeVoucherInfo(DiscountPolicy discountPolicy, long discountAmount) {
        this.discountPolicy = discountPolicy;
        this.discountAmount = discountAmount;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0},{1},{2}{3}",
                voucherId,
                discountPolicy.toString(),
                discountAmount,
                System.lineSeparator()
        );
    }
}
