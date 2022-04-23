package org.prgrms.voucher.models;


public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(long amount, VoucherType voucherType) {
        this.voucherId = null;
        this.discountValue = amount;
        this.voucherType = voucherType;
    }

    @Override
    public long discount() {

        return 0;
    }
}
