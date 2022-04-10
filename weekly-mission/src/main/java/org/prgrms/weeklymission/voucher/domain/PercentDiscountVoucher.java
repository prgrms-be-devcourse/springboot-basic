package org.prgrms.weeklymission.voucher.domain;

import java.util.UUID;

import static org.prgrms.weeklymission.voucher.domain.VoucherType.PERCENT;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final VoucherType voucherType = PERCENT;
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
    public long discount(long beforeDiscount) {
        long afterDiscount = beforeDiscount - (long) (beforeDiscount * (percent / (double) 100));

        Voucher.checkDiscountPrice(beforeDiscount, afterDiscount);

        return afterDiscount;
    }

    @Override
    public String toString() {
        return "VoucherType: " + voucherType
                + " VoucherID: " + voucherId
                + " Percent: " + percent;
    }
}
