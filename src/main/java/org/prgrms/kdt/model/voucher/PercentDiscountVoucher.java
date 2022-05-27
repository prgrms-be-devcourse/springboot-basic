package org.prgrms.kdt.model.voucher;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

public class PercentDiscountVoucher implements Voucher {
    private final UUID id;
    private long percent;
    private LocalDateTime createdAt;

    public PercentDiscountVoucher(UUID id, long percent) {
        this(id, percent, null);
    }

    public PercentDiscountVoucher(UUID id, long percent, LocalDateTime createdAt) {
        checkArgument(id != null, "voucherId must be provided.");
        checkArgument(percent > 0 && percent <= 100, "percent must be greater than 0 and less than or equal to 100");

        this.id = id;
        this.percent = percent;
        this.createdAt = defaultIfNull(createdAt, LocalDateTime.now());
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public long getValue() {
        return percent;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public void changeValue(long percent) {
        checkArgument(percent > 0 && percent <= 100, "percent must be greater than 0 and less than or equal to 100");

        this.percent = percent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("voucherId", id)
            .append("percent", percent)
            .toString();
    }
}
