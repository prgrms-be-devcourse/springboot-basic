package com.prgrms.springbasic.domain.wallet.service;

import com.prgrms.springbasic.domain.customer.dto.CustomerResponse;
import com.prgrms.springbasic.domain.customer.entity.Customer;
import com.prgrms.springbasic.domain.customer.repository.CustomerRepository;
import com.prgrms.springbasic.domain.voucher.dto.VoucherResponse;
import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import com.prgrms.springbasic.domain.voucher.repository.VoucherRepository;
import com.prgrms.springbasic.domain.wallet.dto.WalletRequest;
import com.prgrms.springbasic.domain.wallet.dto.WalletResponse;
import com.prgrms.springbasic.domain.wallet.entity.Wallet;
import com.prgrms.springbasic.domain.wallet.repository.JdbcWalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;
    @Mock
    private JdbcWalletRepository walletRepository;
    @Mock
    private VoucherRepository voucherRepository;
    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setup() {
        walletService = new WalletService(voucherRepository, customerRepository, walletRepository);
    }

    @Test
    @DisplayName("지갑 저장 성공 테스트")
    void testSaveWalletSuccess() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        Voucher voucher = Voucher.createVoucher(UUID.randomUUID(), "percent", 50);
        Wallet wallet = new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId());

        when(customerRepository.findCustomerById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        when(voucherRepository.findVoucherById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));
        when(walletRepository.saveWallet(any(Wallet.class))).thenReturn(wallet);

        WalletResponse response = walletService.saveWallet(new WalletRequest(customer.getCustomerId(), voucher.getVoucherId()));

        assertThat(response.customer_id()).isEqualTo(customer.getCustomerId());
        assertThat(response.voucher_id()).isEqualTo(voucher.getVoucherId());
    }

    @Test
    @DisplayName("해당 회원이 존재하지 않으면 지갑을 생성할 수 없다")
    void testSaveWalletCustomerNotFound() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        Voucher voucher = Voucher.createVoucher(UUID.randomUUID(), "percent", 50);

        when(customerRepository.findCustomerById(customer.getCustomerId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> walletService.saveWallet(new WalletRequest(customer.getCustomerId(), voucher.getVoucherId())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Customer not found");
    }
    @Test
    @DisplayName("해당 바우처가 존재하지 않으면 지갑을 생성할 수 없다")
    void testSaveWalletVoucherNotFound() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        Voucher voucher = Voucher.createVoucher(UUID.randomUUID(), "percent", 50);

        when(customerRepository.findCustomerById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        when(voucherRepository.findVoucherById(voucher.getVoucherId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> walletService.saveWallet(new WalletRequest(customer.getCustomerId(), voucher.getVoucherId())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Voucher not found");
    }

    @Test
    @DisplayName("특정 고객의 Voucher 목록을 가져옴")
    void testGetVouchersByCustomerId() {
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = Voucher.createVoucher(voucherId, "percent", 25);

        List<Voucher> expectedVouchers = List.of(voucher);
        when(walletRepository.findVouchersByCustomerId(customerId)).thenReturn(expectedVouchers);

        List<VoucherResponse> actualVouchers = walletService.getVouchersByCustomerId(customerId);

        assertThat(actualVouchers).hasSize(1);
        assertThat(actualVouchers.get(0).voucherId()).isEqualTo(actualVouchers.get(0).voucherId());
    }

    @Test
    @DisplayName("특정 Voucher의 고객 목록을 가져옴")
    void testGetCustomersByVoucherId() {
        UUID voucherId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "test", "test@gmail.com");

        List<Customer> expectedCustomers = List.of(customer);
        when(walletRepository.findCustomersByVoucherId(voucherId)).thenReturn(expectedCustomers);

        List<CustomerResponse> actualCustomers = walletService.getCustomersByVoucherId(voucherId);

        assertThat(actualCustomers).hasSize(1);
        assertThat(actualCustomers.get(0).customerId()).isEqualTo(customer.getCustomerId());
        assertThat(actualCustomers.get(0).name()).isEqualTo(customer.getName());
        assertThat(actualCustomers.get(0).email()).isEqualTo(customer.getEmail());
    }
}
