package org.prgms.kdt.application.voucher.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class FixedAmountVoucher implements Voucher {
    private UUID voucherId;
    private final VoucherType voucherType = VoucherType.FIXED_AMOUNT;
    private final long amount = 2000L;

    public FixedAmountVoucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", voucherType=" + voucherType +
                ", amount=" + amount +
                '}';
    }
}
