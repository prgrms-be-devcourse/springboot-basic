package com.programmers.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final String type = "FixedAmount";

    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private final UUID voucherID;
    private final long amount;


    public FixedAmountVoucher(UUID voucherID, long amount) {
        if(amount<=0 || amount>MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException();
        this.voucherID = voucherID;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherID() {
        return voucherID;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount-amount;
    }

    public String getType() {
        return type;
    }

    @Override
    public long getDiscount() {
        return amount;
    }
}
