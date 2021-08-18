package org.prgrms.kdt;


import java.util.UUID;

// 인터페이스를 사용하지않은 강한결합도
//public class FixedAmountVoucher {
//    private final long amount;
//
//    public FixedAmountVoucher(long amount) {
//        this.amount = amount;
//    }
//    // voucher가 주어진 금액에 대해서 discount를 하는 로직
//    public long discount(long beforeDiscount){
//        return beforeDiscount - amount;
//    }
//}
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
