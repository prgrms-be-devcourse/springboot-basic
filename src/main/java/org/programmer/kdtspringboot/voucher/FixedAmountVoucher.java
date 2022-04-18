package org.programmer.kdtspringboot.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final Long amount;
    private final static Integer MAX_AMOUNT = 10000;
    private final static Integer MIN_AMOUNT = 0;

    public FixedAmountVoucher(UUID voucherId, Long amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new IllegalArgumentException(MIN_AMOUNT + "~" + MAX_AMOUNT + " 값을 입력해주세요.");
        }
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(Long beforeDiscount) {
        return beforeDiscount - amount > 0 ? beforeDiscount - amount : 0;
    }

    @Override
    public String getInfo() {
        return voucherId + "," + amount + "," + this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                ", class=" + this.getClass().getSimpleName() +
                '}';
    }


}
