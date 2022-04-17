package org.prgrms.kdt.voucher.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.kdt.domain.PercentDiscountVoucher;

class PercentDiscountVoucherTest {

  @Nested
  @DisplayName("PercentAmountVoucher는")
  class Described_constructor{

    @Nested
    @DisplayName("1 ~ 100 사이 정수를 입력하면")
    class Context_with_positive {

      @ParameterizedTest
      @ValueSource(ints = {1, 50, 100})
      @DisplayName("정상적으로 생성된다.")
      void test_constructor_called(int percent) {
        var fixedAmountVoucher = new PercentDiscountVoucher(percent);

        assertThat(fixedAmountVoucher).isNotNull();
      }
    }

    @Nested
    @DisplayName("1 ~ 100 사이 정수를 입력하지 않으면")
    class Context_with_non_positive {

      @ParameterizedTest
      @ValueSource(ints = {-1, 0, 101})
      @DisplayName("예외가 발생한다.")
      void test_constructor_called(int percent) {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(percent));
      }
    }
  }
}