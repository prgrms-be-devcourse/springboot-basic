package org.prgrms.kdtspringvoucher.voucher.service;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    void resetVoucherId();
    Long discount(Long beforeDiscount);
    String getStringForCSV();
}
