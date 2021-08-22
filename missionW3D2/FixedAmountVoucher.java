package com.prgms.missionW3D2;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final long amount;
    private final UUID voucherId;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.amount = amount;
        this.voucherId = voucherId;
    }

    @Override
    public UUID getVoucherId() {
        return null;
    }
    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }
    @Override
    public String getVoucherInfo() {
        return amount + " won Discount Voucher";
    }

}
