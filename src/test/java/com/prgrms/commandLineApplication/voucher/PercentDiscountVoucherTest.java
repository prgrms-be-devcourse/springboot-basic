package com.prgrms.commandLineApplication.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

class PercentDiscountVoucherTest {

  @ParameterizedTest
  @CsvSource(value = {"20|80000", "30|70000", "50|50000"}, delimiter = '|')
  @DisplayName("범위 내의 할인값을 입력했을 경우 PercentDiscount 계산 성공")
  void percentDiscountVoucher_계산_성공(int discountValue, int expected) {
    String voucherType = "percent";
    PercentDiscountVoucher percentDiscountVoucher = PercentDiscountVoucher.of(UUID.randomUUID(), voucherType, discountValue);
    int result = percentDiscountVoucher.discount(100000);
    assertThat(result).isEqualTo(expected);
  }

  @ParameterizedTest
  @ValueSource(ints = {101, 200, 220, -4})
  @DisplayName("범위를 벗어난 할인값을 입력했을 경우 예외 발생 성공")
  void percentDiscountVoucher_할인값_예외_발생(int discountValue) {
    String voucherType = "percent";
    Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
      PercentDiscountVoucher.of(UUID.randomUUID(), voucherType, discountValue);
    });
    assertEquals("Invalid discount amount range (0 ~ 100)", exception.getMessage());
  }

}
