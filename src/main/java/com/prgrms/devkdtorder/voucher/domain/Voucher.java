package com.prgrms.devkdtorder.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long discount(long beforeDiscount);

    long getValue();

    String getName();

    LocalDateTime getCreatedAt();
}
