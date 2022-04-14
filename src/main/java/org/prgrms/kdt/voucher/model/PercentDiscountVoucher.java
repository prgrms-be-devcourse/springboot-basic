package org.prgrms.kdt.voucher.model;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final int MIN_PERCENT = 0;
    private static final int MAX_PERCENT = 100;

    private final UUID voucherId;
    private final long percent;
    private final VoucherType type = VoucherType.PERCENT;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent < MIN_PERCENT || percent > MAX_PERCENT) {
            throw new IllegalArgumentException("퍼센트는 1~100 사이의 값을 입력해주세요.");
        }

        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
            "voucherId=" + voucherId +
            ", percent=" + percent +
            '}';
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (this.percent / 100);
    }

    @Override
    public VoucherType getVoucherType() {
        return this.type;
    }

}
