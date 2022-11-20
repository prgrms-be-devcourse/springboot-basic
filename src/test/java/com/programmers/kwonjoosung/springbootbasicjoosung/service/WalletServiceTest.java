package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherFactory;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.CustomerRepository;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.JdbcCustomerRepository;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.JdbcVoucherRepository;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.VoucherRepository;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.wallet.JdbcWalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class WalletServiceTest {

    private final JdbcWalletRepository jdbcWalletRepositoryMock = mock(JdbcWalletRepository.class);
    private final CustomerRepository jdbcCustomerRepositoryMock = mock(JdbcCustomerRepository.class);
    private final VoucherRepository jdbcVoucherRepositoryMock = mock(JdbcVoucherRepository.class);
    private final WalletService walletService = new WalletService(jdbcWalletRepositoryMock, jdbcVoucherRepositoryMock, jdbcCustomerRepositoryMock);

    @Test
    @DisplayName("[성공] 고객에게 바우처를 할당할 수 있다(지갑에 추가)")
    void insertToWalletTest() {
        //given
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();
        when(jdbcCustomerRepositoryMock.findById(customerId)).thenReturn(Optional.of(new Customer(customerId, "test1")));
        when(jdbcVoucherRepositoryMock.findById(voucherId)).thenReturn(Optional.of(VoucherFactory.createVoucher(VoucherType.FIXED, voucherId, 1000)));
        when(jdbcWalletRepositoryMock.insertToWallet(customerId, voucherId)).thenReturn(true);
        //when
        boolean result = walletService.insertToWallet(customerId, voucherId);
        //then
        assertThat(result).isTrue();
        verify(jdbcCustomerRepositoryMock).findById(customerId);
        verify(jdbcVoucherRepositoryMock).findById(voucherId);
        verify(jdbcWalletRepositoryMock).insertToWallet(customerId, voucherId);
    }

    @Test
    @DisplayName("[실패] 고객테이블에 없는 고객에게는 바우처를 할당할 수 없다")
    void insertNotExistCustomerToWalletTest() {
        //given
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();
        when(jdbcCustomerRepositoryMock.findById(customerId)).thenReturn(Optional.empty());
        //when
        boolean result = walletService.insertToWallet(customerId, voucherId);
        //then
        assertThat(result).isFalse();
        verify(jdbcCustomerRepositoryMock).findById(customerId);
    }

    @Test
    @DisplayName("[실패] 바우처테이블에 없는 바우처를 고객에게 할당할 수 없다")
    void insertNotExistVoucherToWalletTest() {
        //given
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();
        when(jdbcCustomerRepositoryMock.findById(customerId)).thenReturn(Optional.of(new Customer(customerId, "test")));
        when(jdbcVoucherRepositoryMock.findById(voucherId)).thenReturn(Optional.empty());
        //when
        boolean result = walletService.insertToWallet(customerId, voucherId);
        //then
        assertThat(result).isFalse();
        verify(jdbcVoucherRepositoryMock).findById(voucherId);
    }

    @Test
    @DisplayName("[실패] 이미 지갑 테이블에 있는 바우처는 지갑에 추가할 수 없다")
    void insertAlreadyExistVoucherToWalletTest() {
        //given
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();
        when(jdbcCustomerRepositoryMock.findById(customerId)).thenReturn(Optional.of(new Customer(customerId, "test1")));
        when(jdbcVoucherRepositoryMock.findById(voucherId)).thenReturn(Optional.of(VoucherFactory.createVoucher(VoucherType.FIXED, voucherId, 1000)));
        when(jdbcWalletRepositoryMock.findCustomerIdByVoucherId(voucherId)).thenReturn(Optional.of(customerId));
        //when
        boolean result = walletService.insertToWallet(customerId, voucherId);
        //then
        assertThat(result).isFalse();
        verify(jdbcWalletRepositoryMock).findCustomerIdByVoucherId(voucherId);
    }

    @Test
    @DisplayName("[성공] 지갑에 있는 바우처를 삭제할 수 있다")
    void deleteVoucherFromWalletTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        when(jdbcVoucherRepositoryMock.findById(voucherId)).thenReturn(Optional.of(VoucherFactory.createVoucher(VoucherType.FIXED, voucherId, 1000)));
        when(jdbcWalletRepositoryMock.deleteVoucher(voucherId)).thenReturn(true);
        //when
        boolean result = walletService.deleteVoucherFromWallet(voucherId);
        //then
        assertThat(result).isTrue();
        verify(jdbcVoucherRepositoryMock).findById(voucherId);
        verify(jdbcWalletRepositoryMock).deleteVoucher(voucherId);
    }

    @Test
    @DisplayName("[실패] 바우처테이블에 없는 바우처는 지갑에서 삭제할 수 없다")
    void deleteNotExistVoucherFromWalletTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        when(jdbcVoucherRepositoryMock.findById(voucherId)).thenReturn(Optional.empty());
        //when
        boolean result = walletService.deleteVoucherFromWallet(voucherId);
        //then
        assertThat(result).isFalse();
        verify(jdbcVoucherRepositoryMock).findById(voucherId);
    }


    @Test
    @DisplayName("[성공] 바우처Id로 바우처를 소유한 고객을 조회할 수 있다")
    void findCustomerByVoucherId() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test2");
        UUID voucherId = UUID.randomUUID();
        when(jdbcVoucherRepositoryMock.findById(voucherId)).thenReturn(Optional.of(VoucherFactory.createVoucher(VoucherType.FIXED, voucherId, 1000)));
        when(jdbcWalletRepositoryMock.findCustomerIdByVoucherId(voucherId)).thenReturn(Optional.of(customer.getCustomerId()));
        when(jdbcCustomerRepositoryMock.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        //when
        Optional<Customer> customerByVoucherId = walletService.findCustomerByVoucherId(voucherId);
        //then
        assertThat(customerByVoucherId).isPresent();
        assertThat(customerByVoucherId.get()).isEqualTo(customer);
        verify(jdbcVoucherRepositoryMock).findById(voucherId);
        verify(jdbcWalletRepositoryMock).findCustomerIdByVoucherId(voucherId);
    }

    @Test
    @DisplayName("[실패] 바우체 테이블에 없는 바우처Id로 바우처를 소유한 고객을 조회할 수 없다")
    void findCustomerByNotExistVoucherId() {
        //given
        UUID voucherId = UUID.randomUUID();
        when(jdbcVoucherRepositoryMock.findById(voucherId)).thenReturn(Optional.empty());
        //when
        Optional<Customer> customerByVoucherId = walletService.findCustomerByVoucherId(voucherId);
        //then
        assertThat(customerByVoucherId).isEmpty();
        verify(jdbcVoucherRepositoryMock).findById(voucherId);
    }

    @Test
    @DisplayName("[성공] 고객Id로 지갑에 있는 소유한 바우처 리스트를 조회할 수 있다")
    void findVoucherListByCustomerId() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test3");
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT, UUID.randomUUID(), 10);
        when(jdbcWalletRepositoryMock.findVoucherIdsByCustomerId(customer.getCustomerId())).thenReturn(List.of(voucher1.getVoucherId(), voucher2.getVoucherId()));
        when(jdbcCustomerRepositoryMock.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        when(jdbcVoucherRepositoryMock.findById(voucher1.getVoucherId())).thenReturn(Optional.of(voucher1));
        when(jdbcVoucherRepositoryMock.findById(voucher2.getVoucherId())).thenReturn(Optional.of(voucher2));
        //when
        List<Voucher> vouchers = walletService.findVouchersByCustomerId(customer.getCustomerId());
        //then
        assertThat(vouchers).containsExactly(voucher1, voucher2);
        verify(jdbcWalletRepositoryMock).findVoucherIdsByCustomerId(customer.getCustomerId());
        verify(jdbcCustomerRepositoryMock).findById(customer.getCustomerId());
    }

    @Test
    @DisplayName("[실패] 고객 테이블에 없는 고객Id로 지갑에 있는 소유한 바우처 리스트를 조회할 수 없다")
    void findVoucherListByNotExistCustomerId() {
        //given
        UUID customerId = UUID.randomUUID();
        when(jdbcCustomerRepositoryMock.findById(customerId)).thenReturn(Optional.empty());
        //when
        List<Voucher> vouchers = walletService.findVouchersByCustomerId(customerId);
        //then
        assertThat(vouchers).isEmpty();
        verify(jdbcCustomerRepositoryMock).findById(customerId);
    }

}