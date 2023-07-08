package com.prgmrs.voucher.model;

import com.prgmrs.voucher.model.strategy.DiscountStrategy;
import com.prgmrs.voucher.model.vo.DiscountValue;

import java.util.UUID;

public class Voucher {
    private final UUID voucherId;

    private final DiscountStrategy discountStrategy;

    public Voucher(UUID voucherId, DiscountStrategy discountStrategy) {
        this.voucherId = voucherId;
        this.discountStrategy = discountStrategy;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public DiscountValue discount(DiscountValue beforeDiscount) {
        return discountStrategy.discount(beforeDiscount);
    }

    public DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
    }
}