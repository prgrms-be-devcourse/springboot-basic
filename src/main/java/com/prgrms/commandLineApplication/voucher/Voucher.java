package com.prgrms.commandLineApplication.voucher;

import java.util.UUID;

public interface Voucher {
  long discount(long price);
  UUID getVoucherId();
  String getVoucherType();
  long getDiscountAmount();
}
