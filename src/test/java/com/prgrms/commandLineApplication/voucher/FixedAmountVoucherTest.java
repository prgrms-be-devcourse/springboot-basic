package com.prgrms.commandLineApplication.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

class FixedAmountVoucherTest {

  Voucher fixedAmountVoucher;

  @ParameterizedTest
  @CsvSource(value = {"30000|70000", "5000|95000", "10000|90000"}, delimiter = '|')
  @DisplayName("범위 내의 할인값을 입력했을 경우 Fixed Amount 계산 성공")
  void fixedAmountVoucher_계산_성공(double discountAmount, double expected) {
    String voucherType = "fixed";
    fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), voucherType, discountAmount);
    double result = fixedAmountVoucher.discount(100000);
    assertThat(result).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource(value = {"30000|10, 50000|20, 10010|30"}, delimiter = '|')
  @DisplayName("범위를 벗어난 할인값을 입력햇을 경우 Fixed Amount 계산 실패")
  void fixedAmountVoucher_계산_실패(double discountAmount, double expected) {
    String voucherType = "fixed";
    fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), voucherType, discountAmount);
    double result = fixedAmountVoucher.discount(100000);
    assertThat(result).isEqualTo(expected);
  }

}
