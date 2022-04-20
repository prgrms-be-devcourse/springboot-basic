package com.prgrms.vouchermanagement.voucher;

import java.util.UUID;

public interface Voucher {
    long discount(long beforeDiscountPrice);

    UUID getId();
}
