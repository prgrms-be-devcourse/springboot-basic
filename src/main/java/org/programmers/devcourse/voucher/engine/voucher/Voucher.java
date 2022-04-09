package org.programmers.devcourse.voucher.engine.voucher;

import java.util.UUID;

public interface Voucher {

  long getDiscountDegree();

  UUID getVoucherId();

  long discount(long beforeDiscount);


}
