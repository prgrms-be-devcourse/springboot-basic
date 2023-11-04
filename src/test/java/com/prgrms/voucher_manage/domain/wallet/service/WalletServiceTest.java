package com.prgrms.voucher_manage.domain.wallet.service;

import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.customer.service.CustomerService;
import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.service.VoucherService;
import com.prgrms.voucher_manage.domain.wallet.entity.Wallet;
import com.prgrms.voucher_manage.domain.wallet.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.prgrms.voucher_manage.domain.customer.entity.CustomerType.BLACK;
import static com.prgrms.voucher_manage.domain.customer.entity.CustomerType.NORMAL;
import static com.prgrms.voucher_manage.base.ErrorMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {
    @Mock
    private WalletRepository repository;
    @Mock
    private CustomerService customerService;
    @Mock
    private VoucherService voucherService;

    @InjectMocks
    private WalletService service;

    private UUID customerId;
    private UUID voucherId;
    private Wallet wallet;

    @BeforeEach
    void beforeEach() {
        customerId = UUID.randomUUID();
        voucherId = UUID.randomUUID();
        wallet = new Wallet(customerId, voucherId);
    }

    @Test
    @DisplayName("생성된 지갑을 반환받을 수 있다.")
    void saveWallet() {
        when(repository.findByIds(customerId, voucherId)).thenReturn(Optional.empty());
        when(repository.save(any(Wallet.class))).thenReturn(wallet);
        Wallet savedWallet = service.save(wallet);

        assertThat(savedWallet).isNotNull();
        assertThat(customerId).isEqualTo(savedWallet.getCustomerId());
        assertThat(voucherId).isEqualTo(savedWallet.getVoucherId());
    }

    @Test
    @DisplayName("지갑이 이미 존재하면 지갑 생성이 실패한다.")
    void saveWalletFails() {
        when(repository.findByIds(customerId, voucherId)).thenReturn(Optional.of(wallet));

        Exception e = assertThrows(RuntimeException.class, () -> {
            service.save(wallet);
        });

        assertThat(e.getMessage()).isEqualTo(WALLET_ALREADY_EXISTS.getMessage());
    }

    @Test
    @DisplayName("고객 아이디로 지갑 조회")
    void findByCustomerId(){
        Voucher voucher1 = new FixedAmountVoucher(1000L);
        Voucher voucher2 = new PercentDiscountVoucher(10L);
        Wallet wallet1 = new Wallet(customerId, voucher1.getId());
        Wallet wallet2 = new Wallet(customerId, voucher2.getId());

        List<Wallet> wallets = List.of(wallet1, wallet2);

        when(repository.findByCustomerId(customerId)).thenReturn(wallets);

        when(voucherService.findVoucher(voucher1.getId())).thenReturn(voucher1);
        when(voucherService.findVoucher(voucher2.getId())).thenReturn(voucher2);

        List<Voucher> foundVouchers = service.findByCustomerId(customerId);
        assertThat(foundVouchers).containsOnly(voucher1, voucher2);
    }

    @Test
    @DisplayName("회원 아이디로 조회 실패")
    void findByCustomerIdFails(){
        when(repository.findByCustomerId(customerId)).thenReturn(Collections.emptyList());
        Exception e = assertThrows(RuntimeException.class, () -> {
            service.findByCustomerId(customerId);
        });
        assertEquals(WALLET_CUSTOMER_NOT_EXISTS.getMessage(), e.getMessage());
    }

    @Test
    @DisplayName("바우처 아이디로 고객 조회")
    void findByVoucherId(){
        Customer customer1 = new Customer("까마귀", BLACK);
        Customer customer2 = new Customer("까치", NORMAL);
        Wallet wallet1 = new Wallet(customer1.getId(), voucherId);
        Wallet wallet2 = new Wallet(customer2.getId(), voucherId);

        List<Wallet> wallets = List.of(wallet1, wallet2);

        when(repository.findByVoucherId(voucherId)).thenReturn(wallets);

        when(customerService.findById(customer1.getId())).thenReturn(customer1);
        when(customerService.findById(customer2.getId())).thenReturn(customer2);

        List<Customer> foundCustomers = service.findByVoucherId(voucherId);

        assertThat(foundCustomers).containsOnly(customer1, customer2);
    }

    @Test
    @DisplayName("바우처 아이디로 조회 실패")
    void findByVoucherIdFails(){
        when(repository.findByVoucherId(voucherId)).thenReturn(Collections.emptyList());
        Exception e = assertThrows(RuntimeException.class, () -> {
            service.findByVoucherId(voucherId);
        });
        assertEquals(WALLET_VOUCHER_NOT_EXISTS.getMessage(),e.getMessage());
    }

    @Test
    @DisplayName("지갑을 삭제할 수 있다.")
    void deleteWallet(){
        when(repository.findByIds(customerId, voucherId)).thenReturn(Optional.of(wallet));
        service.delete(wallet);

        verify(repository).delete(wallet);
    }

    @Test
    @DisplayName("삭제할 지갑이 없으면 오류를 반환한다.")
    void deleteWalletFails(){
        when(repository.findByIds(customerId, voucherId)).thenReturn(Optional.empty());
        Exception e = assertThrows(RuntimeException.class, () -> service.delete(wallet));

        assertThat(e.getMessage()).isEqualTo(WALLET_DELETE_NOT_EXISTS.getMessage());
    }
}
