package org.programmers.devcourse.voucher.engine.voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.voucher.entity.FixedAmountVoucher;
import org.programmers.devcourse.voucher.engine.voucher.entity.PercentDiscountVoucher;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;

public class VoucherTestUtil {

  private static final LocalDateTime now = LocalDateTime.now();

  public static final List<Voucher> voucherFixtures = List.of(
      FixedAmountVoucher.factory.create(UUID.randomUUID(), 10000L, now.minusDays(4)),
      PercentDiscountVoucher.factory.create(UUID.randomUUID(), 50, now.minusHours(40)),
      PercentDiscountVoucher.factory.create(UUID.randomUUID(), 75, now.minusMinutes(40)),
      PercentDiscountVoucher.factory.create(UUID.randomUUID(), 11, now)
  );

  private VoucherTestUtil() {
  }
}
