package org.prgrms.kdtspringhw.voucher;


import java.util.UUID;


public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }
    @Override
    public UUID getVoucherId(){
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    // voucher가 주어진 금액에 대해서 discount를 하는 로직
    public long discount(long beforeDiscount){
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
