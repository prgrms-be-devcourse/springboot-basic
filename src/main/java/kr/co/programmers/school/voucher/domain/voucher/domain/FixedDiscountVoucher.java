package kr.co.programmers.school.voucher.domain.voucher.domain;

import java.util.UUID;

public class FixedDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedDiscountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }
}