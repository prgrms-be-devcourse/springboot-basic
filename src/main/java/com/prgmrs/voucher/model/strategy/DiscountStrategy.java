package com.prgmrs.voucher.model.strategy;

import com.prgmrs.voucher.model.wrapper.DiscountValue;

public interface DiscountStrategy {
    DiscountValue discount(DiscountValue beforeDiscount);
}
