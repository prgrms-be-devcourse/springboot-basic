package com.prgms.missionW3D2.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    private final UUID voucherId;
    private final long percent;

    @Override
    public UUID getVoucherId() {
        return null;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String getVoucherInfo() {
        return percent + " % Discount Voucher";
    }
}
