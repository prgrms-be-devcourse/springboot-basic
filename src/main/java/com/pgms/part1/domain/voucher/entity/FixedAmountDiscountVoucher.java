package com.pgms.part1.domain.voucher.entity;

import java.util.UUID;

public class FixedAmountDiscountVoucher extends Voucher {
    public FixedAmountDiscountVoucher(UUID id, Integer discount) {
        super(id, discount, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT);
    }
}
