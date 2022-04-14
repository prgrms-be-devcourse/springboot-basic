package org.programmers.kdtspring.entity.voucher;

import org.programmers.kdtspring.entity.voucher.Voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private final UUID voucherId;
    private final long amount;
    private final VoucherType voucherType = VoucherType.FixedAmountVoucher;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public long getDiscount() {
        return this.amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }
}
