package org.prgrms.springbasic.domain.voucher;

import java.text.MessageFormat;
import java.util.UUID;

import static org.prgrms.springbasic.domain.voucher.VoucherType.PERCENT;

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
        return MessageFormat.format("{0}/{1}/{2}\n", voucherId, voucherType, percent);
    }
}
