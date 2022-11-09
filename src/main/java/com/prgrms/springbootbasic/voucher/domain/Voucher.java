package com.prgrms.springbootbasic.voucher.domain;

import java.math.BigDecimal;
import java.util.UUID;

//convention
public interface Voucher {

  String getVoucherType();

  UUID getUUID();

  int getDiscountRate();

  BigDecimal discount(int price);
}
