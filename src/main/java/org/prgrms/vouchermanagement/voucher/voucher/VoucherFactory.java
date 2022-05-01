package org.prgrms.vouchermanagement.voucher.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherFactory {

  public static Voucher createVoucher(int selectedNumber, long reduction, LocalDateTime createdAt) {
    // 여기 에러 어떻게 처리할 것인가
    VoucherType type = VoucherType.fromDbValue(selectedNumber);

    if(type == VoucherType.FIXED_AMOUNT && FixedAmountVoucher.checkReduction(reduction)) {
      return new FixedAmountVoucher(UUID.randomUUID(), reduction, createdAt);
    }
    else if(type == VoucherType.PERCENT_DISCOUNT && PercentDiscountVoucher.checkReduction(reduction)) {
      return new PercentDiscountVoucher(UUID.randomUUID(), reduction, createdAt);
    }
    throw new RuntimeException("[VouherFactory] Voucher를 생성할 수 없습니다");
  }

  public static Voucher createVoucher(VoucherType voucherType, long reduction, LocalDateTime createdAt) {
    if(voucherType == VoucherType.FIXED_AMOUNT && FixedAmountVoucher.checkReduction(reduction)) {
      return new FixedAmountVoucher(UUID.randomUUID(), reduction, createdAt);
    }
    else if(voucherType == VoucherType.PERCENT_DISCOUNT && PercentDiscountVoucher.checkReduction(reduction)) {
      return new PercentDiscountVoucher(UUID.randomUUID(), reduction, createdAt);
    }
    throw new RuntimeException("[VouherFactory] Voucher를 생성할 수 없습니다");
  }


  // Jdbc에서 Voucher를 읽고 Voucher을 생성할 대 사용
  public static Voucher createVoucher(UUID voucherId, long reduction, LocalDateTime createdAt, VoucherType voucherType) {
    if(voucherType == VoucherType.FIXED_AMOUNT && FixedAmountVoucher.checkReduction(reduction)) {
      return new FixedAmountVoucher(UUID.randomUUID(), reduction, createdAt);
    }
    else if(voucherType == VoucherType.PERCENT_DISCOUNT && PercentDiscountVoucher.checkReduction(reduction)) {
      return new PercentDiscountVoucher(UUID.randomUUID(), reduction, createdAt);
    }
    throw new RuntimeException("[VouherFactory] Voucher를 생성할 수 없습니다");
  }
}
