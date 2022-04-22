package org.prgrms.kdt.model.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long getDiscountAmount();

    int getVoucherType();

    LocalDateTime getCreateAt();

//    LocalDateTime getOwnedAt();
}
