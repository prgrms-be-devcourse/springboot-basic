package com.prgmrs.voucher.model;

import com.prgmrs.voucher.model.strategy.DiscountStrategy;
import com.prgmrs.voucher.model.vo.DiscountValue;

import java.util.UUID;

public record Voucher(UUID voucherId, DiscountStrategy discountStrategy) {

    public DiscountValue discount(DiscountValue beforeDiscount) {
        return discountStrategy.discount(beforeDiscount);
    }
}