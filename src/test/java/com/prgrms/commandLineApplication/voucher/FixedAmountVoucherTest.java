package com.prgrms.commandLineApplication.voucher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class FixedAmountVoucherTest {

  @Test
  @DisplayName("FixedAmountVoucher 할인 성공 테스트 코드")
  void FixedAmountSuccess() {
    long price = 100;
    UUID voucherId = UUID.randomUUID();
    long discountValue = 10;
    FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, discountValue);
    Assertions.assertThat(fixedAmountVoucher.discount(100)).isEqualTo(90);
  }

  @Test
  @DisplayName("FixedAmountVoucher 할인 성공 테스트 코드")
  void FixedAmountFailure() {
    long price = 100;
    UUID voucherId = UUID.randomUUID();
    long discountValue = 10;
    FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, discountValue);
    Assertions.assertThat(fixedAmountVoucher.discount(100)).isEqualTo(80);
  }
}
