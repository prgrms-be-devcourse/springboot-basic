package org.prgms.order.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();
    long getAmount();
    VoucherIndexType getType();

    long discount(long beforeDiscount);

    LocalDateTime getCreatedAt();
    LocalDateTime getExpiredAt();

    void setExpiry();
}
