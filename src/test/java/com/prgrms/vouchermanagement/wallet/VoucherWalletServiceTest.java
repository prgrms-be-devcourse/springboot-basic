package com.prgrms.vouchermanagement.wallet;

import com.prgrms.vouchermanagement.customer.Customer;
import com.prgrms.vouchermanagement.customer.CustomerService;
import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.prgrms.vouchermanagement.voucher.VoucherType.FIXED_DISCOUNT;
import static com.prgrms.vouchermanagement.voucher.VoucherType.PERCENT_DISCOUNT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoucherWalletServiceTest {

    @Mock
    VoucherWalletRepository voucherWalletRepository;

    @Mock
    VoucherService voucherService;

    @Mock
    CustomerService customerService;

    @Test
    @DisplayName("Customer 지갑에 Voucher를 추가한다.")
    void addVoucherToWalletTest() {
        // given
        Long sampleCustomerId = 1L;
        Long sampleVoucherId = 2L;
        VoucherWalletService voucherWalletService = new VoucherWalletService(voucherWalletRepository, voucherService, customerService);
        when(customerService.isRegisteredCustomer(anyLong())).thenReturn(true);
        when(voucherService.isRegisteredVoucher(anyLong())).thenReturn(true);

        // when
        voucherWalletService.addVoucherToWallet(sampleCustomerId, sampleVoucherId);

        // then
        verify(voucherWalletRepository).save(any());
    }

    @Test
    @DisplayName("지갑에 Voucher를 추가하는데 존재하지 않는 customerId를 전달하면 예외가 발생한다.")
    void addVoucherWalletNotExistsCustomerTest() {
        // given
        VoucherWalletService voucherWalletService = new VoucherWalletService(voucherWalletRepository, voucherService, customerService);
        Long voucherId = 1L;
        Long wrongCustomerId = -1L;
        when(customerService.isRegisteredCustomer(wrongCustomerId)).thenReturn(false);
        when(voucherService.isRegisteredVoucher(voucherId)).thenReturn(true);

        // then
        assertThatThrownBy(() -> {
            // when
            voucherWalletService.addVoucherToWallet(wrongCustomerId, voucherId);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등록되지 않은 Customer입니다.");

        verify(voucherWalletRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("지갑에 Voucher를 추가하는데 존재하지 않는 voucherId를 전달하면 예외가 발생한다.")
    void addVoucherWalletNotExistsVoucherTest() {
        // given
        VoucherWalletService voucherWalletService = new VoucherWalletService(voucherWalletRepository, voucherService, customerService);
        Long wrongVoucherId = -1L;
        Long customerId = 1L;
        when(voucherService.isRegisteredVoucher(wrongVoucherId)).thenReturn(false);

        // then
        assertThatThrownBy(() -> {
            // when
            voucherWalletService.addVoucherToWallet(customerId, wrongVoucherId);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등록되지 않은 Voucher입니다.");

        verify(voucherWalletRepository, times(0)).save(any());
    }

    // TODO
    @Test
    @DisplayName("wallet에 있는 voucher를 삭제한다.")
    void removeVoucherInWalletTest() {
        // given
        VoucherWalletService voucherWalletService = new VoucherWalletService(voucherWalletRepository, voucherService, customerService);
        Long walletId = 1234L;
        Long customerId = 5678L;
        Long voucherId = 4756L;
        Wallet wallet = Wallet.of(customerId, voucherId);
        when(voucherWalletRepository.findWallet(walletId)).thenReturn(Optional.of(wallet));

        // when
        voucherWalletService.removeVoucherInWallet(walletId);

        // then
        verify(voucherWalletRepository).removeWallet(walletId);
    }

    @Test
    @DisplayName("존재하지 않는 walletId로 wallet에 있는 voucher를 삭제하려하면 예외가 발생한다.")
    void removeVoucherInWalletWrongWalletIdTest() {
        // given
        VoucherWalletService voucherWalletService = new VoucherWalletService(voucherWalletRepository, voucherService, customerService);
        Long wrongWalletId = -1L;
        when(voucherWalletRepository.findWallet(wrongWalletId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            voucherWalletService.removeVoucherInWallet(wrongWalletId);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등록되지 않은 Wallet입니다.");

        verify(voucherWalletRepository, times(0)).removeWallet(wrongWalletId);
    }

    @Test
    @DisplayName("특정 Customer가 가지고 있는 Voucher를 조회한다.")
    void findVoucherByCustomerTest() {
        // given
        Long customerId = 1234L;
        Voucher voucher1 = FIXED_DISCOUNT.constructor(5000, LocalDateTime.now());
        Voucher voucher2 = PERCENT_DISCOUNT.constructor(50, LocalDateTime.now());
        Voucher voucher3 = FIXED_DISCOUNT.constructor(55000, LocalDateTime.now());
        when(voucherService.findVoucherByCustomer(customerId)).thenReturn(List.of(voucher1, voucher2, voucher3));

        // when
        List<Voucher> vouchers = voucherService.findVoucherByCustomer(customerId);

        // then
        assertThat(vouchers.size()).isEqualTo(3);
        assertThat(vouchers).contains(voucher1, voucher2, voucher3);
    }

    @Test
    @DisplayName("특정 Voucher를 가지고 있는 Customer를 조회한다.")
    void findCustomerByVoucherTest() {
        // given
        Customer customer1 = Customer.of("aaa", "aaa@gmail.com");
        Customer customer2 = Customer.of("bbb", "bbb@gmail.com");
        Customer customer3 = Customer.of("ccc", "ccc@gmail.com");
        Long voucherId = 1234L;
        when(customerService.findCustomerByVoucher(voucherId)).thenReturn(List.of(customer1, customer2, customer3));

        // when
        List<Customer> customers = customerService.findCustomerByVoucher(voucherId);

        // then
        assertThat(customers.size()).isEqualTo(3);
        assertThat(customers).contains(customer1, customer2, customer3);
    }
}
