package kdt.vouchermanagement.domain.voucher.domain;

import java.io.Serializable;

public abstract class Voucher implements Serializable {
    private final Long voucherId;
    private final VoucherType voucherType;
    private final int discountValue;

    public Voucher(VoucherType voucherType, int discountValue) {
        this.voucherId = null;
        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

    public Voucher(Long voucherId, VoucherType voucherType, int discountValue) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public abstract void validateValueRange();
}