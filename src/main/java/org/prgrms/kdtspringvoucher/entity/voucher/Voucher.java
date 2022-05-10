package org.prgrms.kdtspringvoucher.entity.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long discount(long beforeDiscount);
    long getAmount();
    int getVoucherTypeNum();
    String getStringForCSV();
    LocalDateTime getCreatedAt();
}
