package com.pgms.part1.domain.voucher.entity;

public class FixedAmountDiscountVoucher extends Voucher {
    public FixedAmountDiscountVoucher(Long id, Integer discount) {
        super(id, discount, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT);
    }
}
