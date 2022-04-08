package org.prgrms.vouchermanager.voucher;


import java.util.UUID;

public class FixedAmountVoucher extends AbstractVoucher {

    private final long amount;

    protected FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId, VoucherType.FIXED);
        this.amount = amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }


    @Override
    public String toString() {
        //TODO : 후에 출력 양식 정하면 StringBuilder나 Message Format으로 바꿀 것
        return "FixedAmountVoucher{" +
                "voucherId=" + getVoucherId() +
                "voucherType=" + getVoucherType() +
                "amount=" + amount +
                '}';
    }
}
