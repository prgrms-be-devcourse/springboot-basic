package org.prgrms.kdt.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long getValue();

    LocalDateTime getCreatedAt();

    VoucherType getType();

    long getMaxValue();

    long discount(long beforeDiscount);

    Voucher changeValue(long value);
}