package org.prgrms.kdt.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getValue();
    Enum<VoucherType> getType();
    LocalDateTime getCreatedAt();
    public long discount(long beforeDiscount);
}
