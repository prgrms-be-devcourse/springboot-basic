package com.prgmrs.voucher.model;

import com.prgmrs.voucher.model.vo.DiscountValue;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    DiscountValue discount(DiscountValue beforeDiscount);
}
