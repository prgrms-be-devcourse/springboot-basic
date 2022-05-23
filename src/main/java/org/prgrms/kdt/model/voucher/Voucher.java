package org.prgrms.kdt.model.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getVoucherValue();
    LocalDateTime getCreatedAt();
    long discount(long beforeDiscount);
    void changeValue(long value);
}
