package org.prgrms.voucher.models;

import java.util.concurrent.atomic.AtomicLong;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(long amount, VoucherType voucherType) {

        this.voucherId = count.incrementAndGet();
        this.discountValue = amount;
        this.voucherType = voucherType;
    }

    @Override
    public long discount() {

        return 0;
    }
}
