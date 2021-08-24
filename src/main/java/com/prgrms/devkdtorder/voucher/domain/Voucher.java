package com.prgrms.devkdtorder.voucher.domain;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long discount(long beforeDiscount);

    long getValue();
}
