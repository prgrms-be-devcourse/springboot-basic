package com.programmers.springbasic.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.springbasic.dto.CustomerDto;
import com.programmers.springbasic.dto.VoucherDto;
import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.entity.voucher.FixedAmountVoucher;
import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.repository.customer.BlacklistCustomerRepository;
import com.programmers.springbasic.repository.customer.CustomerRepository;
import com.programmers.springbasic.repository.voucher.VoucherRepository;
import com.programmers.springbasic.repository.wallet.WalletRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private VoucherRepository voucherRepository;
	@Mock
	private WalletRepository walletRepository;
	@Mock
	private BlacklistCustomerRepository blacklistCustomerRepository;

	@InjectMocks
	private CustomerService customerService;

	private UUID customerId;
	private Customer customer;
	private UUID voucherId;
	private Voucher voucher;

	@BeforeEach
	void setUp() {
		customerId = UUID.randomUUID();
		voucherId = UUID.randomUUID();
		customer = new Customer(customerId, "홍길동", "hong@example.com", LocalDateTime.now());
		voucher = new FixedAmountVoucher(voucherId, 10);
	}

	@Test
	void 블랙리스트_고객을_조회한다() {
		List<Customer> blacklistCustomers = List.of(customer);
		when(blacklistCustomerRepository.getBlacklistCustomers()).thenReturn(blacklistCustomers);

		List<CustomerDto> result = customerService.getBlacklistCustomers();

		assertThat(result, hasSize(1));
	}

	@Test
	void 고객을_생성한다() {
		String name = "홍길동";
		String email = "hong@example.com";
		LocalDateTime now = LocalDateTime.now();
		Customer customer = new Customer(UUID.randomUUID(), name, email, now);
		when(customerRepository.insert(ArgumentMatchers.any(Customer.class))).thenReturn(customer);

		CustomerDto result = customerService.createCustomer(name, email);

		assertThat(result, is(CustomerDto.from(customer)));
	}

	@Test
	void 전체_고객을_조회한다() {
		List<Customer> customers = List.of(customer);
		when(customerRepository.findAll()).thenReturn(customers);

		List<CustomerDto> result = customerService.getAllCustomers();

		assertThat(result, hasSize(1));
	}

	@Test
	void 아이디로_고객을_조회한다() {
		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

		CustomerDto result = customerService.getCustomerById(customerId);

		assertThat(result.id(), is(customerId));
	}

	@Test
	void 아이디로_조회시_존재하지_않는_고객일_경우_예외가_발생한다() {
		when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> customerService.getCustomerById(customerId));
	}

	@Test
	void 고객_세부사항을_변경한다() {
		String newName = "새로운홍길동";
		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

		CustomerDto result = customerService.updateCustomer(customerId, newName);

		assertThat(result.name(), is(newName));
	}

	@Test
	void 존재하지_않는_고객일_경우_변경시_예외_발생한다() {
		when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> customerService.updateCustomer(customerId, "새로운홍길동"));
	}

	@Test
	void 고객을_삭제한다() {
		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
		doNothing().when(customerRepository).deleteById(customerId);

		CustomerDto result = customerService.removeCustomer(customerId);

		assertThat(result.id(), is(customerId));
	}

	@Test
	void 존재하지_않는_고객일_경우_삭제시_예외_발생한다() {
		when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> customerService.removeCustomer(customerId));
	}

	@Test
	void 특정_고객에게_바우처를_할당한다() {
		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
		when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher));
		doNothing().when(walletRepository).insertVoucherForCustomer(customerId, voucherId);

		customerService.assignVoucherToCustomer(customerId, voucherId);

		verify(walletRepository).insertVoucherForCustomer(customerId, voucherId);
	}

	@Test
	void 고객이_보유한_바우처를_조회한다() {
		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
		when(walletRepository.findVoucherIdsByCustomerId(customerId)).thenReturn(List.of(voucherId));
		when(voucherRepository.findAllById(List.of(voucherId))).thenReturn(List.of(voucher));

		List<VoucherDto> result = customerService.getVouchersByCustomer(customerId);

		assertThat(result, hasSize(1));
		assertThat(result.get(0).voucherId(), is(voucherId));
	}

	@Test
	void 고객이_보유한_바우처를_삭제한다() {
		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
		when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher));
		doNothing().when(walletRepository).deleteVoucherFromCustomer(customerId, voucherId);

		customerService.removeVoucherFromCustomer(customerId, voucherId);

		verify(walletRepository).deleteVoucherFromCustomer(customerId, voucherId);
	}

}
