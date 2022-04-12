package org.prgrms.kdtspringvoucher.voucher.service;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    void resetVoucherId();
    long discount(long beforeDiscount);
    String getStringForCSV();
}
