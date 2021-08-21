package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.enums.VoucherType;

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
    public long getDiscount() {
        return amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public VoucherType getVoucherType(){ return VoucherType.FIXED; };

    @Override
    public String toString() {
        return "Fixed Amount Voucher [ " +
                "voucherId = " + voucherId +
                ", amount = " + amount +
                " ]";
    }

}