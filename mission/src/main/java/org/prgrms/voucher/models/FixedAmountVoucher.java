package org.prgrms.voucher.models;


public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(long amount, VoucherType voucherType) {

        super(amount, voucherType);
    }

    public FixedAmountVoucher(Long voucherId, long amount, VoucherType voucherType){

        super(voucherId, amount, voucherType);
    }

    @Override
    public long discount() {

        return 0;
    }
}
