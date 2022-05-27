package org.prgrms.kdt.model.voucher;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

public class FixedAmountVoucher implements Voucher {
    private final UUID id;
    private long amount;
    private LocalDateTime createdAt;

    public FixedAmountVoucher(UUID id, long amount) {
        this(id, amount, null);
    }

    public FixedAmountVoucher(UUID id, long amount, LocalDateTime createdAt) {
        checkArgument(id != null, "voucherId must be provided.");
        checkArgument(amount > 0, "amount must be greater than 0");

        this.id = id;
        this.amount = amount;
        this.createdAt = defaultIfNull(createdAt, now());
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public long getValue() {
        return amount;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
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
            .append("voucherId", id)
            .append("amount", amount)
            .toString();
    }
}
