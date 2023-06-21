package com.devcourse.springbootbasic.engine.voucher;

import java.util.UUID;

public interface Voucher {
    double discountedPrice(long originalPrice);
    UUID getVoucherId();
}
