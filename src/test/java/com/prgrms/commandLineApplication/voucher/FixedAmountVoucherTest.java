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
  @DisplayName("FixedAmountVoucher 할인 계산 결과 일치 테스트")
  void fixedAmountVoucher_계산_성공(Long discountValue, Long result) {
    fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), discountValue);
    assertThat(fixedAmountVoucher.discount(100000L)).isEqualTo(result);
  }

  @ParameterizedTest
  @CsvSource(value = {"30000|0, 50000|0, 10010|0"}, delimiter = '|')
  @DisplayName("FixedAmountVoucher 할인 계산 결과 불일치 테스트")
  void fixedAmountVoucher_계산_실패(Long discountValue, Long result) {
    fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), discountValue);
    assertThat(fixedAmountVoucher.discount(10000L)).isEqualTo(result);
  }
}
