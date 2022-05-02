package com.prgrms.vouchermanagement.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    long discount(long beforeDiscountPrice);

    UUID getVoucherId();

    long getAmount();

    LocalDateTime getCreatedAt();
}
