package org.prgrms.voucherprgrms.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    LocalDateTime getCreatedAt();

    long getValue();

    String getDTYPE();
}
