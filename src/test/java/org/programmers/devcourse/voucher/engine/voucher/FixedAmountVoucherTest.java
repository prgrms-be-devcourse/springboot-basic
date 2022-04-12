package org.programmers.devcourse.voucher.engine.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.programmers.devcourse.voucher.engine.exception.VoucherDataOutOfRangeException;


@DisplayName("FixedAmountVoucher는 ")
class FixedAmountVoucherTest {


  @DisplayName("등록되어 있는 금액 만큼 가격을 할인해야 한다.")
  @ParameterizedTest
  @CsvSource(value = {
      "1500,1000,500",
      "50000, 40000, 10000"
  })
  void discount_price_in_normal_situation(long beforeDiscount, long discountDegree,
      long afterDiscount) throws VoucherDataOutOfRangeException {
    Voucher voucher0 = FixedAmountVoucher.factory.create(UUID.randomUUID(), discountDegree);
    assertThat(voucher0.discount(beforeDiscount)).isEqualTo(afterDiscount);
  }


  @DisplayName("음수가 아닌 금액을 생성자 매개변수로 사용할 경우 예외를 던진다.")
  @ParameterizedTest
  @CsvSource(value = {
      "-1000",
      "-40000"
  })
  void throw_exception_when_discount_degree_is_negative(long discountDegree) {
    assertThatThrownBy(() -> {
      FixedAmountVoucher.factory.create(UUID.randomUUID(), discountDegree);
    }).isInstanceOf(VoucherDataOutOfRangeException.class);
  }

  @DisplayName("할인 대상 가격보다 할인액이 더 클 경우 0을 반환해야 한다.")
  @ParameterizedTest
  @CsvSource(value = {
      "3000,5000",
      "10000,99999"
  })
  void return_zero_when_discount_degree_is_greater_than_target_price(long beforeDiscount,
      long discountDegree) throws VoucherDataOutOfRangeException {
    long discountedPrice = FixedAmountVoucher.factory.create(UUID.randomUUID(), discountDegree)
        .discount(beforeDiscount);

    assertThat(discountedPrice).isZero();
  }

}
