package org.prgrms.springbootbasic.voucher.voucher;

import java.util.UUID;

public abstract class Voucher {

  public abstract UUID getVoucherID();

  public abstract long discount(long beforeDiscount);
}
