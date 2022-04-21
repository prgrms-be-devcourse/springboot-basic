package org.prgrms.kdt.shop.domain;

import org.prgrms.kdt.shop.enums.VoucherType;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private final VoucherType type = VoucherType.FIXED_AMOUNT;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId( ) {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public long getAmount( ) {
        return amount;
    }

    @Override
    public VoucherType getVoucherType( ) {
        return type;
    }

    public VoucherType getType( ) {
        return type;
    }
}
