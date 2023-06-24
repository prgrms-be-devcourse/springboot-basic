package com.prgrms.model.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    Long getVoucherDiscount();

    long discount(long beforeDiscount);
}
