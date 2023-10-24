package com.pgms.part1.domain.voucher.entity;

public class PercentDiscountVoucher extends Voucher {
    public PercentDiscountVoucher(Long id, Integer discount) {
        super(id, discount, VoucherDiscountType.PERCENT_DISCOUNT);
    }
}
