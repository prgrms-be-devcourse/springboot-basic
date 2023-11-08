package org.prgrms.kdt.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {

    UUID voucherId;
    long amount;
    LocalDateTime createdAt;
    UUID customerId = null;

    public abstract long discount(long beforeDiscount);

    public abstract UUID getVoucherId();

    public abstract long getAmount();

    public abstract UUID getCustomerId();

    public abstract void setCustomerId(UUID customerId);

    public abstract String getType();

    public abstract LocalDateTime getCreatedAt();
}
