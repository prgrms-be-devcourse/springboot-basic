package org.prgrms.kdt.model.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    UUID getId();
    long getValue();
    LocalDateTime getCreatedAt();
    long discount(long beforeDiscount);
    void changeValue(long value);
}
