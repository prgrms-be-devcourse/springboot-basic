package org.prgrms.kdt.voucher.domain;

public abstract class Voucher {
    private final long voucherId;
    private final long discountDegree;

    public Voucher(long voucherId, long discountDegree) {
        validateVoucher(discountDegree);
        this.voucherId = voucherId;
        this.discountDegree = discountDegree;
    }

    abstract void validateVoucher(long discountDegree);

    abstract public Voucher changeDiscountDegree(long discountDegree);

    public long getVoucherId() {
        return voucherId;
    }

    abstract public String getTypeName();

    public long getDiscountDegree() {
        return discountDegree;
    }
}
