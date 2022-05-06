package org.prgrms.kdt.model.voucher;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        checkArgument(voucherId != null, "voucherId must be provided.");
        checkArgument(amount > 0, "amount must be greater than 0");

        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getVoucherValue() {
        return amount;
    }


    @Override
    public long discount(long beforeDiscount) {
        long afterDiscount = beforeDiscount - this.amount;
        return afterDiscount < 0 ? 0 : afterDiscount;
    }

    @Override
    public void changeValue(long amount) {
        checkArgument(amount > 0, "amount must be greater than 0");

        this.amount = amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("voucherId", voucherId)
            .append("amount", amount)
            .toString();
    }
}
