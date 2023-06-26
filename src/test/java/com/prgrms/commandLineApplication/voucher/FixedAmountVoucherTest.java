package com.prgrms.commandLineApplication.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

class FixedAmountVoucherTest {

  @ParameterizedTest
  @CsvSource(value = {"30000|70000", "5000|95000", "10000|90000"}, delimiter = '|')
  @DisplayName("범위 내의 할인값을 입력했을 경우 Fixed Amount 계산 성공")
  void fixedAmountVoucher_계산_성공(int discountAmount, int expected) {
    String voucherType = "fixed";
    FixedAmountVoucher fixedAmountVoucher = FixedAmountVoucher.of(UUID.randomUUID(), voucherType, discountAmount);
    int result = fixedAmountVoucher.discount(100000);
    assertThat(result).isEqualTo(expected);
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, -100, -220, -4})
  @DisplayName("범위를 벗어난 할인값을 입력했을 경우 예외 발생 성공")
  void fixedAmountVoucher_할인값_예외_발생(int discountValue) {
    String voucherType = "percent";
    Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
      PercentDiscountVoucher.of(UUID.randomUUID(), voucherType, discountValue);
    });
    assertEquals("Invalid discount amount range (0 ~ )", exception.getMessage());
  }

}
