package com.voucher.vouchermanagement.model.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

  Long discount(Long beforeDiscount);

  UUID getVoucherId();

  Long getValue();

  VoucherType getVoucherType();

  LocalDateTime getCreatedAt();
}
