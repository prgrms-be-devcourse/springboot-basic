package org.prgrms.kdt.model.voucher;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        checkArgument(voucherId != null, "voucherId must be provided.");
        checkArgument(percent > 0, "percent must be greater than 0");

        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getVoucherValue() {
        return percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("voucherId", voucherId)
            .append("percent", percent)
            .toString();
    }
}
