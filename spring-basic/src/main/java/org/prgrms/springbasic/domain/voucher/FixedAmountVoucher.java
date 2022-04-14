package org.prgrms.springbasic.domain.voucher;

import java.text.MessageFormat;
import java.util.UUID;

import static org.prgrms.springbasic.domain.voucher.VoucherType.FIXED;


public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final VoucherType voucherType = FIXED;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        long afterDiscount = beforeDiscount - amount;

        Voucher.checkDiscountPrice(beforeDiscount, afterDiscount);

        return afterDiscount;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}/{1}/{2}\n", voucherId, voucherType, amount);
    }
}
