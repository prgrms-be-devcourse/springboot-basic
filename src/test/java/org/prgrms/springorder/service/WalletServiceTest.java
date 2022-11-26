package org.prgrms.springorder.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
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
	void allocateTest() {
		//given
		MockedStatic<WalletFactory> walletFactoryMockedStatic = Mockito.mockStatic(WalletFactory.class);
		double value = 50;
		Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), value, LocalDateTime.now());
		Customer customer = new Customer(UUID.randomUUID(), "이건우", "1234", LocalDateTime.now(), CustomerType.NORMAL);
		Wallet wallet = new Wallet(UUID.randomUUID(), voucher, customer, LocalDateTime.now());

		Mockito.when(voucherJdbcRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));
		Mockito.when(customerJdbcRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
		Mockito.when(WalletFactory.createWallet(voucher, customer)).thenReturn(wallet);
		Mockito.doNothing().when(walletJdbcRepository).allocate(wallet);

		//when
		walletService.allocate(customer.getCustomerId(), voucher.getVoucherId());

		//then
		Mockito.verify(voucherJdbcRepository).findById(voucher.getVoucherId());
		Mockito.verify(customerJdbcRepository).findById(customer.getCustomerId());
		Mockito.verify(walletJdbcRepository).allocate(wallet);
		walletFactoryMockedStatic.verify(()->WalletFactory.createWallet(voucher,customer));
		walletFactoryMockedStatic.close();

	}

	@Test
	void findVoucherByCustomerIdTest() {

		double value = 150;
		Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), value, LocalDateTime.now());
		Customer customer = new Customer(UUID.randomUUID(), "이건우", "1234", LocalDateTime.now(), CustomerType.NORMAL);

		Mockito.when(walletJdbcRepository.findVoucherByCustomerId(customer.getCustomerId()))
			.thenReturn(List.of(voucher));

		walletService.findVoucherByCustomerId(customer.getCustomerId());

		Mockito.verify(walletJdbcRepository).findVoucherByCustomerId(customer.getCustomerId());
	}

	@Test
	void deleteByVoucherId() {

		//given
		MockedStatic<WalletFactory> walletFactoryMockedStatic = Mockito.mockStatic(WalletFactory.class);
		double value = 50;
		Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), value, LocalDateTime.now());
		Customer customer = new Customer(UUID.randomUUID(), "이건우", "1234", LocalDateTime.now(), CustomerType.NORMAL);
		Wallet wallet = new Wallet(UUID.randomUUID(), voucher, customer, LocalDateTime.now());

		Mockito.when(voucherJdbcRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));
		Mockito.when(customerJdbcRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
		Mockito.when(WalletFactory.createWallet(voucher, customer)).thenReturn(wallet);
		Mockito.doNothing().when(walletJdbcRepository).delete(wallet);

		//when
		walletService.deleteByVoucherId(customer.getCustomerId(), voucher.getVoucherId());

		//then
		Mockito.verify(walletJdbcRepository).delete(wallet);
		walletFactoryMockedStatic.verify(() -> WalletFactory.createWallet(voucher, customer));
		walletFactoryMockedStatic.close();
	}

	@Test
	void findCustomerByVoucherIdTest() {

		//given
		double value = 150;
		Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), value, LocalDateTime.now());
		Customer customer = new Customer(UUID.randomUUID(), "이건우", "1234", LocalDateTime.now(), CustomerType.NORMAL);

		Mockito.when(walletJdbcRepository.findCustomerByVoucherId(voucher.getVoucherId()))
			.thenReturn(List.of(customer));

		walletService.findCustomerByVoucherId(voucher.getVoucherId());

		Mockito.verify(walletJdbcRepository).findCustomerByVoucherId(voucher.getVoucherId());
	}
}
