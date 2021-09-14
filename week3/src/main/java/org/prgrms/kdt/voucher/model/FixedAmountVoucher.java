package org.prgrms.kdt.voucher.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private UUID walletId;
    private final VoucherType voucherType;

    public FixedAmountVoucher(UUID voucherId, long amount, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }
}


