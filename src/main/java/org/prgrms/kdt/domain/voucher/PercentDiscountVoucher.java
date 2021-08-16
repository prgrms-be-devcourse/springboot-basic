package org.prgrms.kdt.domain.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;



    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscount() {
        return percent;
    }

    // 퍼센트에 비율로 얼마를 할인할지에 대한 금액을 리턴함
    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }
}
