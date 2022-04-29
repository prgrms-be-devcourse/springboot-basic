package org.prgrms.kdtspringvoucher.voucher.entity;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long discount(long beforeDiscount);
    long getAmount();
    int getVoucherTypeNum();
    String getStringForCSV();
}
