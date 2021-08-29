package org.prgrms.kdt.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private static final long MAX_VOUCHER_AMOUNT = 100000;
    private final UUID voucherId;
    private final long amount;
    private final String type = "Fixed";

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if(amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if(amount == 0) throw new IllegalArgumentException("Amount should not b zero");
        if(amount > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException("Amount should be less than");
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getVoucherAmount() {
        return amount;
    }

    @Override
    public String getVoucherType() {
        return type;
    }

    public long discount(long beforeDiscount){
        var disCountedAmount = beforeDiscount - amount;
        return (disCountedAmount < 0) ? 0 : disCountedAmount;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Voucher Id : " + voucherId + " ");
        sb.append("Amount : " + amount + " ");
        sb.append("Type : " + type);
        return sb.toString();
    }
}
