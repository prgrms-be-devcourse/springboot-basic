package org.prgrms.voucher.models;


public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(long amount, VoucherType voucherType) {

        super(amount, voucherType);
        validate(amount);
    }

    public FixedAmountVoucher(Long voucherId, long amount, VoucherType voucherType) {

        super(voucherId, amount, voucherType);
    }

    @Override
    public long discount() {

        return 0;
    }

    private void validate(long amount) {

        if (amount < 0) {
            throw new IllegalArgumentException("bad discountValue..");
        }
    }
}
