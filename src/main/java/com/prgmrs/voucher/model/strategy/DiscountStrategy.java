package com.prgmrs.voucher.model.strategy;

import com.prgmrs.voucher.model.vo.DiscountValue;

public interface DiscountStrategy {
    DiscountValue discount(DiscountValue beforeDiscount);
}
