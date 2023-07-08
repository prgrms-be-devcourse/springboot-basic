package prgms.spring_week1.domain.voucher.model;

import prgms.spring_week1.domain.voucher.model.type.VoucherType;

import java.util.UUID;

public class Voucher {
    protected final UUID voucherId = UUID.randomUUID();
    protected final VoucherType voucherType;
    protected final int discount;

    public Voucher(VoucherType voucherType, int discount) {
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getDiscount() {
        return discount;
    }
}
