package com.prgrms.commandLineApplication.voucher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class PercentDiscountVoucherTest {

  @Test
  @DisplayName("PercentDiscountVoucher 할인 성공 테스트 코드")
  void PercentDiscountSuccess() {
    long price = 100;
    UUID voucherId = UUID.randomUUID();
    long discountValue = 25;
    PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, discountValue);
    System.out.println("percentDiscountVoucher = " + percentDiscountVoucher.discount(price));
    Assertions.assertThat(percentDiscountVoucher.discount(price)).isEqualTo(75);
  }

  @Test
  @DisplayName("PercentDiscountVoucher 할인 실패 테스트 코드")
  void PercentDiscountFailure() {
    long price = 100;
    UUID voucherId = UUID.randomUUID();
    long discountValue = 20;
    PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, discountValue);
    Assertions.assertThat(percentDiscountVoucher.discount(price)).isEqualTo(70);
  }
}
