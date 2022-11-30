package org.prgrms.voucher.voucherType;

import java.time.LocalDateTime;
import java.util.UUID;
import org.prgrms.voucher.discountType.Amount;
import org.springframework.stereotype.Component;

@Component
public interface Voucher {
  long discount(long beforeDiscount);

  UUID getVoucherId();

  Amount getVoucherAmount();

  VoucherType getVoucherType();

  Voucher changeAmountValue(long amount);

  LocalDateTime getDate();

}
