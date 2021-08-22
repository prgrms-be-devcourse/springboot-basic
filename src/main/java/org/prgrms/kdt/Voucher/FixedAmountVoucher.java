package org.prgrms.kdt.Voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;
    private  final TypeStatus type=TypeStatus.Fixed;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;

    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public TypeStatus getType() {
        return type;
    }


    @Override
    public long getVoucherdiscount() {
        return amount;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }



}
