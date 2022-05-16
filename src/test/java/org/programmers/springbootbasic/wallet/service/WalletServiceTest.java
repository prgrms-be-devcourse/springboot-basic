package org.programmers.springbootbasic.wallet.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.customer.model.Customer;
import org.programmers.springbootbasic.voucher.model.FixedAmountVoucher;
import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.wallet.domain.Wallet;
import org.programmers.springbootbasic.wallet.repository.WalletRepository;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class WalletServiceTest {
    WalletRepository walletRepositoryMock = mock(WalletRepository.class);

    WalletService walletService = new WalletService(walletRepositoryMock);

    @Test
    @DisplayName("고객이 가지고 있는 바우처를 불러올 수 있다.")
    void testGetVoucher() {
        Voucher fixedAmountVoucher = mock(FixedAmountVoucher.class);
        Customer customer = mock(Customer.class);
        List<Voucher> voucherList = List.of(fixedAmountVoucher);
        given(walletService.getVouchers(customer.getCustomerId())).willReturn(voucherList);

        walletService.getVouchers(customer.getCustomerId());

        then(walletRepositoryMock).should(times(1)).findVoucherByCustomerId(customer.getCustomerId());
    }

    @Test
    @DisplayName("바우처를 가지고 있는 고객을 불러올 수 있다.")
    void testGetCustomer() {
        Voucher fixedAmountVoucher = mock(FixedAmountVoucher.class);
        Customer customer = mock(Customer.class);
        List<Customer> customersList = List.of(customer);
        given(walletService.getCustomers(fixedAmountVoucher.getVoucherId())).willReturn(customersList);

        walletService.getCustomers(fixedAmountVoucher.getVoucherId());

        then(walletRepositoryMock).should(times(1)).findCustomerByVoucherId(fixedAmountVoucher.getVoucherId());
    }

    @Test
    @DisplayName("월렛을 고객에게 할당할 수 있다.")
    void testAllocateWallet() {
        Wallet wallet = mock(Wallet.class);
        given(walletRepositoryMock.insert(wallet)).willReturn(wallet);

        walletService.allocateVoucher(wallet);

        then(walletRepositoryMock).should(times(1)).insert(wallet);
    }

    @Test
    @DisplayName("월렛을 고객에게서 삭제할 수 있다.")
    void testDeleteWallet() {
        Wallet wallet = mock(Wallet.class);

        walletService.deleteWallet(wallet.getWalletId());

        then(walletRepositoryMock).should(times(1)).deleteById(wallet.getWalletId());
    }
}