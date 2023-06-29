package org.prgrms.kdt.voucher;

import java.text.MessageFormat;
import java.util.UUID;


public class FixedAmountVoucher implements Voucher {
    private final long amount;
    private final UUID voucherId;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discountAppliedPrice(long beforeDiscount) {
        long finalPrice = beforeDiscount - amount;
        return finalPrice > 0 ? finalPrice : 0;
    }

    @Override
    public long getVoucherDiscountValue() {
        return amount;
    }

    @Override
    public String getVoucherName() {
        return MessageFormat.format("[{0}]", getClass().getSimpleName());
    }
}
