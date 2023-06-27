package com.prgrms.commandLineApplication.service;

import com.prgrms.commandLineApplication.repository.MemoryVoucherRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class VoucherServiceTest {

  private final VoucherService voucherService = new VoucherService(new MemoryVoucherRepository());

  @ParameterizedTest
  @CsvSource(value = {"pe|10", "fix|10", "hello|10"}, delimiter = '|')
  @DisplayName("잘못된 타입을 입력했을 경우 예외 발생 성공")
  void fixedAmountVoucher_할인값_예외_발생(String voucherType, int discountValue) {
    Assertions.assertThatThrownBy(() -> voucherService.create(voucherType, discountValue))
            .isInstanceOf(IllegalArgumentException.class);
  }

}
