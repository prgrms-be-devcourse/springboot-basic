package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public abstract class Voucher {
    public UUID voucherId;
    public final double amount = 20;

    public Voucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public double getAmount() {
        return amount;
    }

    public abstract String getVoucherType();

    public abstract double discount(double beforeDiscount);
}
