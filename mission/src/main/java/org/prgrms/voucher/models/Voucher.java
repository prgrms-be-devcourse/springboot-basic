package org.prgrms.voucher.models;

public abstract class Voucher {

    protected Long voucherId;
    protected long discountValue;
    protected VoucherType voucherType;

    public Long getVoucherId() {

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
