package com.devcourse.springbootbasic.engine.voucher.domain;

import java.util.UUID;

public interface Voucher {
    double discountedPrice(long originalPrice);
    UUID getVoucherId();
    String toString();
}
