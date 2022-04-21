package org.prgrms.voucher.models;

public class FixedAmountVoucher extends Voucher{

    public FixedAmountVoucher(Long voucherId, long amount, VoucherType voucherType) {

        this.voucherId = voucherId;
        this.discountValue = amount;
        this.voucherType = voucherType;
    }

    @Override
    public long discount() {

        return 0;
    }
}
