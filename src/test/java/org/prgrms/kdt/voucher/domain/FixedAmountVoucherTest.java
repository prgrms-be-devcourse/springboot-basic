package org.prgrms.kdt.voucher.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.kdt.domain.FixedAmountVoucher;

@DisplayName("FixedAmountVoucher 클래스")
class FixedAmountVoucherTest {

  @Nested
  @DisplayName("FixedAmountVoucher는")
  class Described_constructor {

    @Nested
    @DisplayName("양수 값을 입력하면")
    class Context_with_positive {

      @Test
      @DisplayName("정상적으로 생성된다.")
      void test_constructor_called() {
        var fixedAmountVoucher = new FixedAmountVoucher(100);

        assertThat(fixedAmountVoucher).isNotNull();
      }
    }

    @Nested
    @DisplayName("양수가 아닌 값을 입력하면")
    class Context_with_non_positive {

      @ParameterizedTest
      @ValueSource(ints = {-1, 0})
      @DisplayName("예외가 발생한다.")
      void test_constructor_called(int amount) {
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(amount));
      }
    }
  }
}