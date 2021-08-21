package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        validate(voucherId, amount); // TODO beforeAmount에 대한 값을 알게되면, amount에 대한 validate 로직을 재설계할 필요 존재
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public void validate(UUID voucherId, long amount) {
        if (voucherId == null) {
            throw new RuntimeException();
        }
    }

    @Override
    public UUID voucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "Fixed " +
                voucherId +
                " " + amount;
    }
}
