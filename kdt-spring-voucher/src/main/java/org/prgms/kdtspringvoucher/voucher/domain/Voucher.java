package org.prgms.kdtspringvoucher.voucher.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherID();
    Long getAmount();
}
