package com.prgrms.commandLineApplication.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.commandLineApplication.discount.PercentDiscount;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

class PercentDiscountVoucherTest {

  @ParameterizedTest
  @CsvSource(value = {"percent|20|80000|100000", "percent|30|70000|100000", "percent|50|50000|100000"}, delimiter = '|')
  @DisplayName("범위 내의 할인값을 입력했을 경우 Percent Discount 계산 성공")
  void percentDiscountVoucher_계산_성공(String voucherType, int discountAmount, int expected, int price) {
    Voucher createdPercentVoucher = VoucherFactory.createVoucher(voucherType, discountAmount);
    int result = createdPercentVoucher.supplyDiscount(price);
    assertThat(result).isEqualTo(expected);
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, -100, 200})
  @DisplayName("범위를 벗어난 할인값을 입력했을 경우 예외 발생 성공")
  void percentDiscountVoucher_할인값_예외_발생(int discountAmount) {
    Assertions.assertThatThrownBy(() -> new Voucher(UUID.randomUUID(), PercentDiscount.of(discountAmount)))
            .isInstanceOf(IllegalArgumentException.class);
  }

}
