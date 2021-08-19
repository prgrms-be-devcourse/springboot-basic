package org.prgrms.kdt.VO;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private final long percent;
    private final String toStringGuide1 = "voucher ID : ";
    private final String toStringGuide2 = "% 할인 voucher";
    private final UUID voucherId;
    public PercentDiscountVoucher(long percent, UUID voucherId){
        this.percent = percent;
        this.voucherId = voucherId;
    }

    @Override
    public long discount(Long beforeDiscount) {
        return beforeDiscount*(percent/100);
    }

    @Override
    public String toString() {
        return toStringGuide1 + voucherId + ", " + percent + toStringGuide2;
    }
}
