package org.programmers.applicationcontext.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) { // 바우쳐 id와 할인할 금액 정보를 갖는 생성자
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getVoucherVolume() {
        return amount;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }
}
