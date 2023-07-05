package org.prgms.vouchermanagement.voucher.domain.entity;

import org.prgms.vouchermanagement.voucher.VoucherType;

import java.util.UUID;

public class VoucherImpl implements Voucher{

    private final UUID voucherId;
    private final long percent;
    private final VoucherType voucherType;

    public VoucherImpl(UUID voucherId, long percent, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.voucherType = voucherType;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long returnDiscount() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }
}
