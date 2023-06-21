package com.programmers.voucher.domain.voucher;

import java.util.UUID;

public interface Voucher {
    String getVoucherId();

    long discount(long originalPrice);

}
