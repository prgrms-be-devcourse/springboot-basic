package org.prgrms.voucher.models;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Voucher {

    protected Long voucherId;
    protected long discountValue;
    protected VoucherType voucherType;

    public long getVoucherId() {

        return voucherId;
    }

    public long getDiscountValue() {

        return discountValue;
    }

    public VoucherType getVoucherType() {

        return voucherType;
    }

    abstract public long discount();
}
