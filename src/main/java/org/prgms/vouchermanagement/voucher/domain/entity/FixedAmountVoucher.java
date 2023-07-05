package org.prgms.vouchermanagement.voucher.domain.entity;

import org.prgms.vouchermanagement.voucher.VoucherType;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private final VoucherType voucherType;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = VoucherType.FIXED_AMOUNT_VOUCHER_TYPE;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long returnDiscount() {
        return amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }
}
