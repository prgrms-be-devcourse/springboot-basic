package com.prgrms.vouchermanagement.voucher;

import java.time.LocalDateTime;

public interface Voucher {
    long discount(long beforeDiscountPrice);

    Long getVoucherId();

    long getAmount();

    LocalDateTime getCreatedAt();
}
