package org.prgrms.vouchermanagement.voucher.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    VoucherType getVoucherType();
    int getDiscountAmount();
}
