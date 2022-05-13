package org.programmers.devcourse.voucher.engine.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

  long getDiscountDegree();

  UUID getVoucherId();

  LocalDateTime getCreatedAt();

  long discount(long beforeDiscount);
  
}
