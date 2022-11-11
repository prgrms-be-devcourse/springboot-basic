package com.programmers.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final String type = "FixedAmount";
    private final UUID voucherID;
    private final long amount;


    public FixedAmountVoucher(UUID voucherID, long amount) {
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
