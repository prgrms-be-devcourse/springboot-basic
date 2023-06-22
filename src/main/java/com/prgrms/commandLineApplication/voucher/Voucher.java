package com.prgrms.commandLineApplication.voucher;

import java.util.UUID;

public interface Voucher {
  UUID getVoucherId();
  long getDiscountValue();
  long discount(long price);
}
