package org.prgrms.kdt.Model;

import org.prgrms.kdt.TypeStatus;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;
    private  final TypeStatus type;

    public FixedAmountVoucher(UUID voucherId, long amount,TypeStatus type) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.type=TypeStatus.Fixed;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }


    @Override
    public long getdiscount() {
        return amount;
    }
    public TypeStatus getType() {
        return type;
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
