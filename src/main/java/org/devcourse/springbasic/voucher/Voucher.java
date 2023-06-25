package org.devcourse.springbasic.voucher;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long getDiscountRate();

    long discount(long beforeDiscount);

}
