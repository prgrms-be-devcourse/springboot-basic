package com.example.commandlineapplication.domain.voucher.model;

import java.util.UUID;

public interface Voucher {

  UUID getVoucherId();
  long discountedPrice(long beforeDiscount);

}
