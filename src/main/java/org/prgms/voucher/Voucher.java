package org.prgms.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforeDiscount);

    long getAmount();

    long getPercent();

    VoucherType getVoucherType();
}
