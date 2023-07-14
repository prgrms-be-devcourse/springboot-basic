package com.prgrms.commandLineApplication.service;

import com.prgrms.commandLineApplication.voucher.discount.DiscountType;
import com.prgrms.commandLineApplication.voucher.dto.VoucherCreateDto;
import com.prgrms.commandLineApplication.voucher.dto.VoucherResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

class VoucherServiceTest {

  private final VoucherService voucherService;

  public VoucherServiceTest(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @ParameterizedTest
  @CsvSource(value = {"pe|10", "fix|10", "hello|10"}, delimiter = '|')
  @DisplayName("잘못된 타입을 입력했을 경우 예외가 발생한다.")
  void enterWrongVoucherType_ThrowException(String voucherType, int discountAmount) {
    VoucherCreateDto createDto = new VoucherCreateDto(voucherType, discountAmount);
    Assertions.assertThatThrownBy(() -> voucherService.create(createDto))
            .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("id로 바우처를 찾을 수 있다.")
  void findVoucherById_Success() {
    String discountType = "percent";
    int discountAmount = 15;
    VoucherCreateDto createDto = new VoucherCreateDto(discountType, discountAmount);
    UUID id = voucherService.create(createDto);

    VoucherResponseDto responseDto = voucherService.findById(id);
    DiscountType findDiscountType = responseDto.discountType();

    Assertions.assertThat(findDiscountType.toString()).isEqualTo(discountType.toUpperCase());
  }

}
