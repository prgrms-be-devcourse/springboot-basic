package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherFactory;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.wallet.JdbcWalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class walletServiceTest {

    CustomerService customerServiceMock = Mockito.mock(CustomerService.class);
    JdbcWalletRepository jdbcWalletRepositoryMock = Mockito.mock(JdbcWalletRepository.class);
    VoucherService voucherServiceMock = Mockito.mock(VoucherService.class);
    WalletService walletService = new WalletService(jdbcWalletRepositoryMock,customerServiceMock,voucherServiceMock);

    @Test
    @DisplayName("지갑에 추가할 수 있다")
    void insertWallet() {
        //given
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();
        when(jdbcWalletRepositoryMock.insert(customerId, voucherId)).thenReturn(1);
        //when
        boolean result = walletService.assignVoucherToCustomer(customerId, voucherId);
        //then
        assertTrue(result);
        verify(jdbcWalletRepositoryMock).insert(customerId, voucherId);
    }

    @Test
    @DisplayName("지갑에 있는 바우처를 삭제할 수 있다")
    void deleteWallet() {
        //given
        UUID voucherId = UUID.randomUUID();
        //when
        when(jdbcWalletRepositoryMock.delete(voucherId)).thenReturn(1);
        boolean result = walletService.deleteVoucherFromWallet(voucherId);
        //then
        assertTrue(result);
        verify(jdbcWalletRepositoryMock).delete(voucherId);
    }


    @Test
    @DisplayName("바우처Id로 바우처를 소유한 고객을 조회할 수 있다")
    void findCustomerByVoucherId() {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "joosung");
        UUID voucherId = UUID.randomUUID();
        //when
        when(jdbcWalletRepositoryMock.findByVoucherId(voucherId)).thenReturn(customerId);
        when(customerServiceMock.findCustomerByCustomerId(customerId)).thenReturn(customer);
        Customer foundCustomerById = walletService.findCustomerByVoucherId(voucherId);
        //then
        assertEquals(customer, foundCustomerById);
        verify(jdbcWalletRepositoryMock).findByVoucherId(voucherId);
        verify(customerServiceMock).findCustomerByCustomerId(customerId);
    }

    @Test
    @DisplayName("고객Id로 지갑에 있는 바우처 리스트를 조회할 수 있다")
    void findVoucherListByCustomerId() {
        //given
        UUID customerId = UUID.randomUUID();

        UUID voucherId1 = UUID.randomUUID();
        UUID voucherId2 = UUID.randomUUID();
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED, voucherId1, 1000);
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT, voucherId2, 10);

        //when
        when(jdbcWalletRepositoryMock.findByCustomerId(customerId)).thenReturn(List.of(voucherId1, voucherId2));
        when(voucherServiceMock.findVoucher(voucherId1)).thenReturn(voucher1);
        when(voucherServiceMock.findVoucher(voucherId2)).thenReturn(voucher2);
        List<Voucher> vouchers = walletService.findAllVouchersFromWallet(customerId);
        //then
        assertEquals(voucher1, vouchers.get(0));
        assertEquals(voucher2,vouchers.get(1));
        verify(jdbcWalletRepositoryMock).findByCustomerId(customerId);
        verify(voucherServiceMock).findVoucher(voucherId1);
        verify(voucherServiceMock).findVoucher(voucherId2);
    }
}