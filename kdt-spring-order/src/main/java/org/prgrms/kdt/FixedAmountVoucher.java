package org.prgrms.kdt;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final long amount;
    private final String type = "Fixed";

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucher() {
        return null;
    }

    public long discount(long beforeDiscount){
        return beforeDiscount - amount;
    }

    @Override
    public String showInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append("Voucher Id : " + voucherId + " ");
        sb.append("Amount : " + amount + " ");
        sb.append("Type : " + type);
        return sb.toString();
    }
}
