package org.prgrms.voucher.discountType;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.voucher.voucherType.VoucherType;

class DiscountRateTest {

  @ParameterizedTest
  @ValueSource(longs = {-1L, 101L})
  @DisplayName("입력받은 할인율이 적거나 많은 경우에 IllegalStateException을 던진다")
  void test(long value) {
    //given
    VoucherType percent = VoucherType.PERCENT;
    //when&then
    assertThrows(IllegalStateException.class, () -> percent.generateAmount(value));
  }

}
