package com.prgrms.w3springboot.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long getAmount();

    long discount(long beforeDiscount);
}
