package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherFactory;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.wallet.JdbcWalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class WalletServiceTest {

    private final JdbcWalletRepository jdbcWalletRepositoryMock = mock(JdbcWalletRepository.class);
    private final WalletService walletService = new WalletService(jdbcWalletRepositoryMock);

    @Test
    @DisplayName("[성공] 지갑에 바우처를 할당하는 기능을 테스트 한다.")
    void insertToWalletTest() {
        //given
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();
        //when
        walletService.insertToWallet(customerId, voucherId);
        //then
        verify(jdbcWalletRepositoryMock).insertToWallet(customerId, voucherId);
    }


    @Test
    @DisplayName("[성공] 지갑에 있는 바우처를 삭제하는 기능을 테스트 한다.")
    void deleteVoucherFromWalletTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        //when
        walletService.deleteVoucherFromWallet(voucherId);
        //then
        verify(jdbcWalletRepositoryMock).deleteVoucher(voucherId);
    }

    @Test
    @DisplayName("[성공] 지갑에 있는 바우처의 id로 소유 고객을 조회하는 기능을 테스트 한다.")
    void findCustomerByVoucherId() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test2");
        UUID voucherId = UUID.randomUUID();
        when(jdbcWalletRepositoryMock.findCustomerByVoucherId(voucherId)).thenReturn(customer);
        //when
        walletService.findCustomerByVoucherId(voucherId);
        //then
        verify(jdbcWalletRepositoryMock).findCustomerByVoucherId(voucherId);
    }

    @Test
    @DisplayName("[성공] 고객Id로 지갑에 있는 소유한 바우처 리스트를 조회할 수 있다.")
    void findVoucherListByCustomerId() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test3");
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT, UUID.randomUUID(), 10);
        when(jdbcWalletRepositoryMock.findVouchersByCustomerId(customer.getCustomerId()))
                .thenReturn(List.of(voucher1, voucher2));
        //when
        walletService.findWalletByCustomerId(customer.getCustomerId());
        //then
        verify(jdbcWalletRepositoryMock).findVouchersByCustomerId(customer.getCustomerId());
    }

}