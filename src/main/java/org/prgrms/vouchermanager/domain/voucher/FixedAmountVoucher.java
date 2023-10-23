package org.prgrms.vouchermanager.domain.voucher;



import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final long amount;

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
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
