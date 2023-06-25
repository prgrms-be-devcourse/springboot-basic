package com.prgrms.commandLineApplication.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

class PercentDiscountVoucherTest {

  Voucher percentDiscountVoucher;

  @ParameterizedTest
  @CsvSource(value = {"20|80000", "30|70000", "50|50000"}, delimiter = '|')
  @DisplayName("범위 내의 할인값을 입력했을 경우 PercentDiscount 계산 성공")
  void percentDiscountVoucher_계산_성공(double discountValue, double expected) {
    String voucherType = "percent";
    percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), voucherType, discountValue);
    double result = percentDiscountVoucher.discount(100000);
    assertThat(result).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource(value = {"101|0, 200|0, 220|0"}, delimiter = '|')
  @DisplayName("범위를 벗어난 할인값을 입력했을 경우 PercentDiscount 계산 실패")
  void percentDiscountVoucher_계산_실패(double discountValue, double expected) {
    String voucherType = "percent";
    percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), voucherType, discountValue);
    double result = percentDiscountVoucher.discount(100000);
    assertThat(result).isEqualTo(expected);
  }

}
