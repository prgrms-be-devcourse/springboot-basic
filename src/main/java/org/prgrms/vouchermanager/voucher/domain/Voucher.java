package org.prgrms.vouchermanager.voucher.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    VoucherType getVoucherType();
    Long discount(long beforeDiscount);
}
