package com.prgrms.devkdtorder;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long discount(long beforeDiscount);

    long getValue();
}
