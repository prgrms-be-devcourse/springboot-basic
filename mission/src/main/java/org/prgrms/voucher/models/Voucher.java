package org.prgrms.voucher.models;

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

    public void setVoucherId(Long voucherId) {

        this.voucherId = voucherId;
    }

    abstract public long discount();
}
