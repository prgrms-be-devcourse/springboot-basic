package com.prgrms.commandLineApplication.voucher;

import java.util.UUID;

public interface Voucher {
  double discount(double price);
  UUID getVoucherId();
  String getVoucherType();
  double getDiscountAmount();
}
