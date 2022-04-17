package org.prgrms.kdt.voucher.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.kdt.domain.VoucherType;

@DisplayName("VoucherType 클래스")
class VoucherTypeTest {

  @Nested
  @DisplayName("of 메소드는")
  class Describe_of {

    @Nested
    @DisplayName("존재하는 코드를 입력하면")
    class Context_with_valid_arguments {

      @ParameterizedTest
      @ValueSource(ints = {1, 2})
      @DisplayName("VoucherType를 생성한다.")
      void it_return_voucher_type(int code) {
        VoucherType voucherType = VoucherType.of(code);

        assertThat(voucherType.getCode()).isEqualTo(code);
      }
    }

    @Nested
    @DisplayName("존재하지 않는 코드를 입력하면")
    class Context_with_invalid_arguments {

      @Test
      @DisplayName("IllegalArgumentException을 발생시킨다.")
      void it_throw_illegal_argument_exception() {
        assertThrows(IllegalArgumentException.class, () -> VoucherType.of(0));
      }
    }
  }

  @Test
  void create() {
  }
}