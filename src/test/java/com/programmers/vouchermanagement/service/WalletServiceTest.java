package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.domain.voucher.FixedAmountVoucher;
import com.programmers.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.wallet.Wallet;
import com.programmers.vouchermanagement.dto.wallet.CreateWalletRequestDto;
import com.programmers.vouchermanagement.dto.wallet.GetWalletsRequestDto;
import com.programmers.vouchermanagement.repository.customer.CustomerRepository;
import com.programmers.vouchermanagement.repository.voucher.VoucherRepository;
import com.programmers.vouchermanagement.repository.wallet.WalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class WalletServiceTest {

    private WalletRepository walletRepository;
    private CustomerRepository customerRepository;
    private VoucherRepository voucherRepository;
    private WalletService walletService;

    public WalletServiceTest() {
        this.walletRepository = Mockito.mock(WalletRepository.class);
        this.customerRepository = Mockito.mock(CustomerRepository.class);
        this.voucherRepository = Mockito.mock(VoucherRepository.class);
        this.walletService = new WalletService(walletRepository, customerRepository, voucherRepository);
    }

    @Test
    @DisplayName("특정 고객에게 바우처를 할당할 수 있다.")
    void createWallet() {
        // given
        Customer mockCustomer = new Customer(UUID.randomUUID(), "test@email.com", false);
        Voucher mockVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);

        CreateWalletRequestDto request = CreateWalletRequestDto.builder()
                .customerId(mockCustomer.getId())
                .voucherId(mockVoucher.getId())
                .build();

        given(customerRepository.findById(mockCustomer.getId())).willReturn(Optional.of(mockCustomer));
        given(voucherRepository.findById(mockVoucher.getId())).willReturn(Optional.of(mockVoucher));

        // when
        walletService.createWallet(request);

        // then
        verify(walletRepository).save(any(Wallet.class));
    }

    @Test
    @DisplayName("존재하지 않는 고객에게 바우처를 할당할 수 없다.")
    void createWallet_notFoundCustomer_fail() {
        // given
        UUID notFoundCustomerId = UUID.randomUUID();
        Voucher mockVoucher = new FixedAmountVoucher(notFoundCustomerId, 1000L);

        CreateWalletRequestDto request = CreateWalletRequestDto.builder()
                .customerId(notFoundCustomerId)
                .voucherId(mockVoucher.getId())
                .build();

        given(customerRepository.findById(notFoundCustomerId)).willReturn(Optional.empty());
        given(voucherRepository.findById(mockVoucher.getId())).willReturn(Optional.of(mockVoucher));

        // when & then
        assertThatThrownBy(() -> walletService.createWallet(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not found customer");
    }

    @Test
    @DisplayName("특정 고객에게 존재하지 않는 바우처를 할당할 수 없다.")
    void createWallet_notfoundVoucher_fail() {
        // given
        Customer mockCustomer = new Customer(UUID.randomUUID(), "test@email.com", false);
        UUID notFoundVoucherId = UUID.randomUUID();

        CreateWalletRequestDto request = CreateWalletRequestDto.builder()
                .customerId(mockCustomer.getId())
                .voucherId(notFoundVoucherId)
                .build();

        given(customerRepository.findById(mockCustomer.getId())).willReturn(Optional.of(mockCustomer));
        given(voucherRepository.findById(notFoundVoucherId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> walletService.createWallet(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not found voucher");
    }

    @Test
    @DisplayName("지갑 목록을 조회할 수 있다.")
    void getWallets() {
        // given
        Customer mockCustomer = new Customer(UUID.randomUUID(), "test@email.com", false);
        Voucher mockVoucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        Voucher mockVoucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 10L);

        List<Wallet> mockWallets = List.of(
                new Wallet(mockCustomer, mockVoucher1),
                new Wallet(mockCustomer, mockVoucher2)
        );

        GetWalletsRequestDto request = new GetWalletsRequestDto();

        given(walletRepository.findAll(any())).willReturn(mockWallets);

        // when
        List<Wallet> wallets = walletService.getWallets(request);

        // then
        assertThat(wallets).hasSize(2);
        assertThat(wallets).extracting(Wallet::getCustomer)
                .containsExactlyInAnyOrder(mockCustomer, mockCustomer);
        assertThat(wallets).extracting(Wallet::getVoucher)
                .containsExactlyInAnyOrder(mockVoucher1, mockVoucher2);
    }

    @Test
    @DisplayName("고객의 바우처를 삭제할 수 있다.")
    void deleteWallet() {
        // given
        Customer mockCustomer = new Customer(UUID.randomUUID(), "test@email.com", false);
        Voucher mockVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);

        Wallet mockWallet = new Wallet(1, mockCustomer, mockVoucher, false);

        given(walletRepository.findById(mockWallet.getId())).willReturn(Optional.of(mockWallet));

        // when
        walletService.deleteWallet(mockWallet.getId(), mockCustomer.getId());

        // then
        verify(walletRepository).deleteById(mockWallet.getId());
    }
}
