package com.prgrms.commandLineApplication.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

class FixedAmountVoucherTest {

  @ParameterizedTest
  @CsvSource(value = {"fixed|30000|70000|100000", "fixed|5000|95000|100000", "fixed|10000|90000|100000"}, delimiter = '|')
  @DisplayName("범위 내의 할인값을 입력했을 경우 Fixed Amount 계산 성공")
  void fixedAmountVoucher_계산_성공(String voucherType, int discountAmount, int expected, int price) {
    Voucher createdFixedVoucher = VoucherFactory.createVoucher(voucherType, discountAmount);
    int result = createdFixedVoucher.discount(price);
    assertThat(result).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource(value = {"fixed|-1", "fixed|-220", "fixed|-4"}, delimiter = '|')
  @DisplayName("범위를 벗어난 할인값을 입력했을 경우 예외 발생 성공")
  void fixedAmountVoucher_할인값_예외_발생(String voucherType, int discountAmount) {
    Assertions.assertThatThrownBy(() -> FixedAmountVoucher.of(UUID.randomUUID(), voucherType, discountAmount))
            .isInstanceOf(IllegalArgumentException.class);
  }

}
