package org.programmers.devcourse.voucher.engine.voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.voucher.entity.FixedAmountVoucher;
import org.programmers.devcourse.voucher.engine.voucher.entity.PercentDiscountVoucher;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;

public class VoucherTestUtil {

  public static final List<Voucher> voucherFixtures = List.of(
      FixedAmountVoucher.factory.create(UUID.randomUUID(), 10000L, LocalDateTime.now()),
      PercentDiscountVoucher.factory.create(UUID.randomUUID(), 50, LocalDateTime.now()),
      PercentDiscountVoucher.factory.create(UUID.randomUUID(), 75, LocalDateTime.now()),
      PercentDiscountVoucher.factory.create(UUID.randomUUID(), 11, LocalDateTime.now())
  );

  private VoucherTestUtil() {
  }
}
