package org.prgms.order.voucher.entity;

import java.util.UUID;
import java.util.regex.Pattern;

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

    public long discount(long beforeDiscount){
        return beforeDiscount - amount;
    }

    @Override
    public String getVoucherInfo() {
        return "FixedAmountVoucher     " +
                ", Discount = " + amount;
    }

}
