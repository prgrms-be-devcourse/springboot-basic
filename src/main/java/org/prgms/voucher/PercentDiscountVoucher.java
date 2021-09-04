package org.prgms.voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final long MAX_VOUCHER_PERCENT = 100;


    private final UUID voucherId;
    private final long percent;


    public PercentDiscountVoucher(UUID voucherId, long percent, VoucherType voucherType) {
        if (percent < 0) throw new IllegalArgumentException("percent should be positive");
        if (percent == 0) throw new IllegalArgumentException("percent should not be zero");
        if (percent > MAX_VOUCHER_PERCENT)
            throw new IllegalArgumentException("percent should be less than %d".formatted(MAX_VOUCHER_PERCENT));
        this.voucherId = voucherId;
        this.percent = percent;
    }


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount * (percent / (double) 100));
    }

    @Override
    public long getAmount() {
        return 0;
    }

    @Override
    public long getPercent() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT_DISCOUNT;
    }
    
    @Override
    public String toString() {
        return MessageFormat.format("[PercentDiscountVoucher] voucherId : {0}, percent : {1}", voucherId, percent);
    }

}
