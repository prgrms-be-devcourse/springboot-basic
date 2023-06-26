package com.prgrms.commandLineApplication.service;

import com.prgrms.commandLineApplication.repository.MemoryVoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class VoucherServiceTest {

  VoucherService voucherService = new VoucherService(new MemoryVoucherRepository());

  @ParameterizedTest
  @ValueSource(strings = {"fix", "pe", "hello", "test"})
  @DisplayName("잘못된 타입을 입력했을 경우 예외 발생 성공")
  void fixedAmountVoucher_할인값_예외_발생(String voucherType) {
    int discountValue = 10;
    Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
      voucherService.create(voucherType, discountValue);
    });
    assertEquals("Invalid Type", exception.getMessage());
  }

}
