package org.prgrms.voucher.models;

import java.util.concurrent.atomic.AtomicLong;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(AtomicLong voucherId, long amount, VoucherType voucherType) {

        this.voucherId = voucherId;
        this.discountValue = amount;
        this.voucherType = voucherType;
    }

    @Override
    public long discount() {

        return 0;
    }
}
