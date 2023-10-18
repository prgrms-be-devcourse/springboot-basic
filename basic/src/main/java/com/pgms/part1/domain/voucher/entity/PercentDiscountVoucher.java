package com.pgms.part1.domain.voucher.entity;

public class PercentDiscountVoucher extends Voucher {
    public PercentDiscountVoucher(Integer discount) {
        super(discount, VoucherDiscountType.PERCENTDISCOUNT);
    }
}