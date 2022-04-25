package com.dojinyou.devcourse.voucherapplication.voucher.domain;

public class PercentAmountVoucher extends Voucher {
    public PercentAmountVoucher(VoucherType type, PercentAmount amount) {
        super(null, type, amount);
    }

    public PercentAmountVoucher(Long id, VoucherType type, VoucherAmount amount) {
        super(id, type, amount);
    }

    @Override
    public double discount(double originAmount) {
        PercentAmount percentAmount = (PercentAmount) getVoucherAmount();
        double remainRate = percentAmount.getRemainRate();
        return originAmount * remainRate;
    }
}
