package org.programmers.kdt.voucher;

import java.text.MessageFormat;
import java.util.Objects;
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
    public long getDiscountAmount(long beforeDiscount) {
        return Math.min(amount, beforeDiscount);
    }

    @Override
    public String toString() {
        return MessageFormat.format("<< Fixed Amount Voucher >> \nID : {0}\nDiscount : ${1}", voucherId, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (null == o || o.getClass() != FixedAmountVoucher.class) {
            return false;
        }
        FixedAmountVoucher voucher = (FixedAmountVoucher)o;
        return voucherId.equals(voucher.getVoucherId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(voucherId);
    }
}
