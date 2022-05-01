package org.prgrms.voucherapp.engine.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

    long getAmount();

    UUID getVoucherId();

    String getTypeName();

    LocalDateTime getCreatedAt();

    long discount(long beforeDiscount);
}
