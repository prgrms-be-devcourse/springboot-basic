package com.prgrms.commandLineApplication.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.commandLineApplication.voucher.discount.FixedDiscount;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

class FixedAmountVoucherTest {

  @ParameterizedTest
  @CsvSource(value = {"fixed|3000|7000|10000", "fixed|5000|95000|100000", "fixed|1000|9000|10000"}, delimiter = '|')
  @DisplayName("범위 내의 할인값을 입력했을 경우 Fixed Amount 계산 성공")
  void fixedAmountVoucher_계산_성공(String voucherType, int discountAmount, int expected, int price) {
    Voucher createdFixedVoucher = VoucherFactory.createVoucher(voucherType, discountAmount);
    int result = createdFixedVoucher.supplyDiscount(price);
    assertThat(result).isEqualTo(expected);
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, -100, 200000})
  @DisplayName("범위를 벗어난 할인값을 입력했을 경우 예외 발생 성공")
  void fixedAmountVoucher_할인값_예외_발생(int discountAmount) {
    Assertions.assertThatThrownBy(() -> new Voucher(UUID.randomUUID(), FixedDiscount.of(discountAmount)))
            .isInstanceOf(IllegalArgumentException.class);
  }

}
