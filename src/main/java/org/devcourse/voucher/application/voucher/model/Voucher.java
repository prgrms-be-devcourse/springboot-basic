package org.devcourse.voucher.application.voucher.model;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforeDiscount);

    long getDiscount();
}
