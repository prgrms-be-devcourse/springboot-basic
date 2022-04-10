package org.prgrms.weeklymission.voucher.domain;

import java.util.UUID;

import static org.prgrms.weeklymission.voucher.domain.VoucherType.FIXED;

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
        return "VoucherType: " + voucherType
                + " VoucherID: " + voucherId
                + " DiscountAmount: " + amount;
    }
}
