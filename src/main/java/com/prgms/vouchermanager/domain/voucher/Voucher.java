package com.prgms.vouchermanager.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    UUID getId();

    long getDiscountValue();

    VoucherType getVoucherType();

    LocalDateTime getCreatedAt();

}
