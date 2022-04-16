package org.prgrms.VoucherManagement.voucher.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherFactory {

  public static Voucher createVoucher(int selectedNumber, int reduction, LocalDateTime createdAt) {
    // 여기 에러 어떻게 처리할 것인가
    VoucherType type = VoucherType.fromInteger(selectedNumber);

    if(type == VoucherType.FIXED_AMOUNT && FixedAmountVoucher.checkReduction(reduction)) {
      return new FixedAmountVoucher(UUID.randomUUID(), reduction, createdAt);
    }
    else if(type == VoucherType.PERCENT_DISCOUNT && PercentDiscountVoucher.checkReduction(reduction)) {
      return new PercentDiscountVoucher(UUID.randomUUID(), reduction, createdAt);
    }
    throw new RuntimeException("[VouherFactory] Voucher를 생성할 수 없습니다");
  }
}
