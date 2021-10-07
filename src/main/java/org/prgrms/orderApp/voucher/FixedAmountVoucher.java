package org.prgrms.orderApp.voucher;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.UUID;


public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final long amount;

    public  FixedAmountVoucher(UUID voucherId, long amount) {
        Assert.isTrue(amount > 0 , "Fixed 바우처 할인 금액은 0보다 커야 합니다.");
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        Assert.isTrue(amount < beforeDiscount, "할인 가격은 상품 가격보다 작거나 같아야 합니다.");
        return beforeDiscount - amount;
    }

    @Override
    public long getVoucherAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }

}
