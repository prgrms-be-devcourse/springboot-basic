package com.prgrms.w3springboot.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long getAmount();

    String getVoucherType();

    long discount(long beforeDiscount);
}
