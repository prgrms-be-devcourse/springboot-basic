package org.programmers.kdt.weekly.voucher.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public final class FixedAmountVoucher implements Voucher {
    private UUID voucherId;
    private final VoucherType voucherType = VoucherType.FixedVoucherRepository;
    private int value;

    public FixedAmountVoucher(UUID voucherId, int amount) {
        this.voucherId = voucherId;
        this.value = amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - value;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Voucher Type: " + voucherType +
                ", voucherId: " + voucherId  +
                ", percent: " + value;
    }

}

