package org.prgrms.kdt.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.kdt.dto.VoucherDto;

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
        var voucherDto = new VoucherDto(UUID.randomUUID(), UUID.randomUUID(), 100L);
        var fixedAmountVoucher = new FixedAmountVoucher(voucherDto);

        assertThat(fixedAmountVoucher).isNotNull();
      }
    }

    @Nested
    @DisplayName("양수가 아닌 값을 입력하면")
    class Context_with_non_positive {

      @ParameterizedTest
      @ValueSource(longs = {-1, 0})
      @DisplayName("예외가 발생한다.")
      void test_constructor_called(Long amount) {
        var voucherDto = new VoucherDto(UUID.randomUUID(), UUID.randomUUID(), amount);

        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(voucherDto));
      }
    }
  }
}