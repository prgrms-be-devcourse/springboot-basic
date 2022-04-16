package org.programmers.devcourse.voucher.engine.voucher.entity;

import java.util.UUID;

public interface Voucher {

  long getDiscountDegree();

  UUID getVoucherId();

  long discount(long beforeDiscount);


}
