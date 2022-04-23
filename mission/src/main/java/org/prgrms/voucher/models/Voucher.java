package org.prgrms.voucher.models;

public abstract class Voucher {

    private final Long voucherId;
    private final long discountValue;
    private final VoucherType voucherType;

    protected Voucher(long discountValue, VoucherType voucherType) {

        this.voucherId = null;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

    protected Voucher(Long voucherId, long discountValue, VoucherType voucherType) {

        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

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
