package org.prgrms.kdt.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.domain.Customer;
import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.repository.VoucherRepository;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

  @Mock
  VoucherRepository voucherRepository;

  @InjectMocks
  VoucherService voucherService;

  @Nested
  @DisplayName("assign은")
  class Describe_register {

    @Nested
    @DisplayName("해당하는 바우처가 없으면")
    class Context_with_non_exist_voucher {

      @Test
      @DisplayName("에러를 반환한다.")
      void it_throws_exception() {
        var voucherId = UUID.randomUUID();
        given(voucherRepository.findById(voucherId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> voucherService.assign(voucherId, UUID.randomUUID()))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("Error: Voucher does not exist");
      }
    }

    @Nested
    @DisplayName("해당하는 바우처가 있으면")
    class Context_with_exist_voucher {

      @Test
      @DisplayName("사용자에게 바우처를 할당한다.")
      void it_assign_voucher() {
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), UUID.randomUUID(), 30L);
        var customer = new Customer("abc", "abc@gmail.com");
        given(voucherRepository.findById(any())).willReturn(Optional.of(voucher));
        given(voucherRepository.update(any())).willReturn(Optional.of(new FixedAmountVoucher(
            voucher.getVoucherId(), customer.getCustomerId(), voucher.getAmount())));

        var sut = voucherService.assign(voucher.getVoucherId(),
            customer.getCustomerId());

        assertThat(sut.getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(sut.getCustomerId()).isEqualTo(customer.getCustomerId());
        assertThat(sut.getAmount()).isEqualTo(voucher.getAmount());
        then(voucherRepository).should(times(1)).findById(any());
        then(voucherRepository).should(times(1)).update(any());
      }
    }
  }
}