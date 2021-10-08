package org.prgrms.kdt.voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(final UUID voucherId, final long percent) {
        if (percent >= 100 || percent <= 0) throw new IllegalArgumentException("할인률은 0 초과, 100 미만이어야 합니다.");
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getVoucherDiscount() {
        return percent;
    }

    @Override
    public String getVoucherType() {
        return "PercentDiscountVoucher";
    }

    @Override
    public long discount(final long beforeDiscount) {
        final double afterDiscount = beforeDiscount * (1 - (percent / 100.0));
        return (long) afterDiscount;
    }

    @Override
    public String toString() {
        return MessageFormat.format("PercentDiscountVoucher( {0}% ), voucher_id = {1}", percent, voucherId.toString());
    }
}
