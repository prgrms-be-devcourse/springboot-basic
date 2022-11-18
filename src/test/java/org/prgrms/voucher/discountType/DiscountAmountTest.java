package org.prgrms.voucher.discountType;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.voucher.voucherType.VoucherType;


class DiscountAmountTest {
  @DisplayName("입력받은 할인금액이 1보다 작을 때 IllegalStateException을 던진다")
  @Test
  void incorrectlyEnteredDiscountRate2() {
    //given
    VoucherType fixed = VoucherType.FIXED;
    long value = -1L;
    //when&then
    assertThrows(IllegalStateException.class, () -> fixed.generateAmount(value));
  }
}