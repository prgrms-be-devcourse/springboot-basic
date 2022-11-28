package org.prgrms.springorder.service;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.customer.CustomerType;
import org.prgrms.springorder.domain.voucher.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.wallet.Wallet;
import org.prgrms.springorder.domain.wallet.WalletFactory;
import org.prgrms.springorder.repository.customer.CustomerJdbcRepository;
import org.prgrms.springorder.repository.voucher.VoucherJdbcRepository;
import org.prgrms.springorder.repository.wallet.WalletJdbcRepository;
import org.prgrms.springorder.service.wallet.WalletService;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

	@Mock
	WalletJdbcRepository walletJdbcRepository;

	@Mock
	VoucherJdbcRepository voucherJdbcRepository;

	@Mock
	CustomerJdbcRepository customerJdbcRepository;

	@InjectMocks
	WalletService walletService;

	@Test
	@DisplayName("아이디를 통해 고객과 바우처를 성공적으로 반환받고 respository에 allocate 메서드를 성공적으로 실행한다.")
	void allocateTest() {
		//given
		MockedStatic<WalletFactory> walletFactoryMockedStatic = mockStatic(WalletFactory.class);
		double value = 50;
		Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), value, LocalDateTime.now());
		Customer customer = new Customer(UUID.randomUUID(), "이건우", "1234", LocalDateTime.now(), CustomerType.NORMAL);
		Wallet wallet = new Wallet(UUID.randomUUID(), voucher, customer, LocalDateTime.now());

		when(voucherJdbcRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));
		when(customerJdbcRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
		when(WalletFactory.createWallet(voucher, customer)).thenReturn(wallet);
		doNothing().when(walletJdbcRepository).allocate(wallet);

		//when
		walletService.allocate(customer.getCustomerId(), voucher.getVoucherId());

		//then
		verify(voucherJdbcRepository).findById(voucher.getVoucherId());
		verify(customerJdbcRepository).findById(customer.getCustomerId());
		verify(walletJdbcRepository).allocate(wallet);
		walletFactoryMockedStatic.verify(()->WalletFactory.createWallet(voucher,customer));
		walletFactoryMockedStatic.close();

	}

	@Test
	@DisplayName("고객 아디를 통해 바우처를 성공적으로 반환 받는다.")
	void findVoucherByCustomerIdTest() {
		//given
		double value = 150;
		Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), value, LocalDateTime.now());
		Customer customer = new Customer(UUID.randomUUID(), "이건우", "1234", LocalDateTime.now(), CustomerType.NORMAL);

		when(walletJdbcRepository.findVoucherByCustomerId(customer.getCustomerId()))
			.thenReturn(List.of(voucher));

		//when
		walletService.findVoucherByCustomerId(customer.getCustomerId());

		//then
		verify(walletJdbcRepository).findVoucherByCustomerId(customer.getCustomerId());
	}

	@Test
	@DisplayName("아이디를 통해 성공적으로 바우처와 고객을 받아 지갑 객체를 만들어 delete 메서드를 성공적으로 실행한다.")
	void deleteByVoucherId() {

		//given
		MockedStatic<WalletFactory> walletFactoryMockedStatic = mockStatic(WalletFactory.class);
		double value = 50;
		Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), value, LocalDateTime.now());
		Customer customer = new Customer(UUID.randomUUID(), "이건우", "1234", LocalDateTime.now(), CustomerType.NORMAL);
		Wallet wallet = new Wallet(UUID.randomUUID(), voucher, customer, LocalDateTime.now());

		when(voucherJdbcRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));
		when(customerJdbcRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
		when(WalletFactory.createWallet(voucher, customer)).thenReturn(wallet);
		doNothing().when(walletJdbcRepository).delete(wallet);

		//when
		walletService.deleteByVoucherId(customer.getCustomerId(), voucher.getVoucherId());

		//then
		verify(walletJdbcRepository).delete(wallet);
		walletFactoryMockedStatic.verify(() -> WalletFactory.createWallet(voucher, customer));
		walletFactoryMockedStatic.close();
	}

	@Test
	@DisplayName("바우처 아이디를 통해 고객을 성공적으로 조회한다.")
	void findCustomerByVoucherIdTest() {

		//given
		double value = 150;
		Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), value, LocalDateTime.now());
		Customer customer = new Customer(UUID.randomUUID(), "이건우", "1234", LocalDateTime.now(), CustomerType.NORMAL);

		when(walletJdbcRepository.findCustomerByVoucherId(voucher.getVoucherId()))
			.thenReturn(List.of(customer));

		//when
		walletService.findCustomerByVoucherId(voucher.getVoucherId());

		//then
		verify(walletJdbcRepository).findCustomerByVoucherId(voucher.getVoucherId());
	}
}
