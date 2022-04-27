package org.prgms.management.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    String getVoucherType();

    String getVoucherName();

    int getDiscountNum();

    LocalDateTime getCreatedAt();
}
