package org.prgrms.deukyun.voucherapp.entity;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{

    private final UUID id;
    public final long amount;

    public FixedAmountVoucher(UUID id, long amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return id;
    }

    @Override
    public long discount(long beforeDiscount){
        return beforeDiscount - amount;
    }

}
