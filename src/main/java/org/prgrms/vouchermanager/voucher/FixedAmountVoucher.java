package org.prgrms.vouchermanager.voucher;


import java.util.UUID;

public class FixedAmountVoucher extends AbstractVoucher {

    private final long amount;

    // UUID를 생성자로 입력받지 않고 내부에서 생성하도록 작성
    protected FixedAmountVoucher(long amount) {
        super(UUID.randomUUID(), VoucherType.FIXED);
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
