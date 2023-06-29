package prgms.spring_week1.domain.voucher.model;

import prgms.spring_week1.domain.voucher.model.type.VoucherType;

import java.util.UUID;

public abstract class Voucher {
    protected final UUID voucherId;
    protected final VoucherType voucherType;
    protected final long discount;

    public Voucher(UUID voucherId, VoucherType voucherType, long discount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getDiscount() {
        return discount;
    }
}
