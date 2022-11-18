package org.prgrms.vouchermanagement.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.customer.repository.CustomerRepository;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.domain.VoucherType;
import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class VoucherCreateServiceTest {

    private final VoucherRepository voucherRepository = mock(VoucherRepository.class);
    private final CustomerRepository customerRepository = mock(CustomerRepository.class);
    private final VoucherCreateService voucherCreateService = new VoucherCreateService(voucherRepository, customerRepository);

    @Test
    @DisplayName("FixedAmountVoucher 정상 생성 확인")
    void createFixedAmountVoucher() {
        // given
        String voucherTypeInput = "1";
        int discountValue = 1000;

        Customer customer = Customer.createNormalCustomer(UUID.randomUUID(), "custonerName", "customer@google.com", LocalDateTime.now());
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));

        // when
        Voucher voucher = voucherCreateService.createVoucher(voucherTypeInput, discountValue, customer.getEmail());

        // then
        verify(voucherRepository).save(voucher);
        verify(customerRepository).findByEmail(customer.getEmail());
        assertThat(voucher.getVoucherType()).isEqualTo(VoucherType.FIXED_AMOUNT);
        assertThat(voucher.getDiscountAmount()).isEqualTo(discountValue);
    }

    @Test
    @DisplayName("PercentDiscountVoucher 정상 생성 확인")
    void createPercentDiscountVoucher() {
        // given
        String voucherTypeInput = "2";
        int discountValue = 50;
        UUID customerId = UUID.randomUUID();
        Customer customer = Customer.createNormalCustomer(customerId, "name", "email@google.com", LocalDateTime.now());

        when(customerRepository.findByEmail(customer.getEmail()))
                .thenReturn(Optional.of(customer));

        // when
        Voucher voucher = voucherCreateService.createVoucher(voucherTypeInput, discountValue, customer.getEmail());

        // then
        verify(voucherRepository).save(voucher);
        verify(customerRepository).findByEmail(customer.getEmail());

        assertThat(voucher.getVoucherType()).isEqualTo(VoucherType.PERCENT_DISCOUNT);
        assertThat(voucher.getDiscountAmount()).isEqualTo(discountValue);
        assertThat(voucher.getCustomerId()).isEqualTo(customerId);
    }

}