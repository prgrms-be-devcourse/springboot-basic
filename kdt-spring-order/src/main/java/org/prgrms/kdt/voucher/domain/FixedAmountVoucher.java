package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getVoucherValue() {
        return amount;
    }

    @Override
    public Enum<VoucherType> getVoucherType() {
        return VoucherType.FIXED;
    }

    public long discount(long beforeDiscount){
        return beforeDiscount - amount;
    }


}
