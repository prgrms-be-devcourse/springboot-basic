package com.dojinyou.devcourse.voucherapplication.voucher.domain;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(VoucherType type, FixedAmount amount) {
        super(null, type, amount);
    }

    public FixedAmountVoucher(Long id, VoucherType type, FixedAmount amount) {
        super(id, type, amount);
    }

    @Override
    public double discount(double originAmount) {
        int discountAmount = getVoucherAmount().getAmount();
        return originAmount - discountAmount;
    }
}
