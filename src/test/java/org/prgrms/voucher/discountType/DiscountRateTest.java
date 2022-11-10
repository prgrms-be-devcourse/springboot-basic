package org.prgrms.voucher.discountType;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.voucher.voucherType.VoucherType;

class DiscountRateTest {
  @DisplayName("입력받은 할인율이 -1 또는 100 초과 일때 IllegalStateException을 던진다")
  @Test
  void incorrectlyEnteredDiscountRate() {
    //given
    VoucherType percent = VoucherType.PERCENT;
    String minusValue = "-1";
    String overHundred = "101";
    //when&then
    assertAll(
        () ->  assertThrows(IllegalStateException.class, () -> VoucherType.generateAmount(percent, minusValue)),
        () ->  assertThrows(IllegalStateException.class, () -> VoucherType.generateAmount(percent, overHundred))
    );
  }

  @DisplayName("입력받은 할인율이 숫자가 아닐 때 NumberFormatException을 던진다")
  @Test
  void whenDiscountRateNotANumber() {
    //given
    VoucherType percent = VoucherType.PERCENT;
    String value = "가";
    //when&then
    assertThrows(NumberFormatException.class, () -> VoucherType.generateAmount(percent, value));
  }

}