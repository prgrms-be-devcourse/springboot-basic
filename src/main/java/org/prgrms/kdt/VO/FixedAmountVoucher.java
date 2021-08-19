package org.prgrms.kdt.VO;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final long amount;
    private final String toStringGuide1 = "voucher ID : ";
    private final String toStringGuide2 = "원 할인 voucher";
    private final UUID voucherId;

    public FixedAmountVoucher(long amount, UUID voucherId){
        this.amount = amount;
        this.voucherId = voucherId;
    }

    @Override
    public long discount(Long beforeDiscount) {
        return beforeDiscount-amount;
    }

    @Override
    public String toString() {
        return toStringGuide1 + voucherId + ", " + amount + toStringGuide2;
    }
}
