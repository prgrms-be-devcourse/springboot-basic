package org.prgrms.kdt.voucher;

import java.util.UUID;

public class CreateVoucher {
    long amount;
    float percent;

    public CreateVoucher(final long amount) {
        this.amount = amount;
    }

    public CreateVoucher(final float percent) {
        this.percent = percent;
    }


    public FixedAmountVoucher createFixedAmountVoucher() {
        System.out.println("FixedAmountVoucher가 생성되었습니다.");
        return new FixedAmountVoucher(UUID.randomUUID(), amount);
    }

    public PercentDiscountVoucher createPercentDiscountVoucher() {
        System.out.println("PercentDiscountVoucher가 생성되었습니다.");
        return new PercentDiscountVoucher(UUID.randomUUID(), percent);
    }
}
