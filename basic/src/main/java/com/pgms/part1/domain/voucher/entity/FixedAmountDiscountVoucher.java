package com.pgms.part1.domain.voucher.entity;

public class FixedAmountDiscountVoucher extends Voucher {
    public FixedAmountDiscountVoucher(Integer discount) {
        super(discount, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT);
    }
}