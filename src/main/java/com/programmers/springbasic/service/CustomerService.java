package com.programmers.springbasic.service;

import static com.programmers.springbasic.constants.ErrorCode.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.programmers.springbasic.dto.CustomerDto;
import com.programmers.springbasic.dto.VoucherDto;
import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.repository.customer.BlacklistCustomerRepository;
import com.programmers.springbasic.repository.customer.CustomerRepository;
import com.programmers.springbasic.repository.voucher.VoucherRepository;
import com.programmers.springbasic.repository.wallet.WalletRepository;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final VoucherRepository voucherRepository;
	private final WalletRepository walletRepository;
	private final BlacklistCustomerRepository blacklistCustomerRepository;

	public CustomerService(CustomerRepository customerRepository,
		VoucherRepository voucherRepository, WalletRepository walletRepository,
		BlacklistCustomerRepository blacklistCustomerRepository) {
		this.customerRepository = customerRepository;
		this.voucherRepository = voucherRepository;
		this.walletRepository = walletRepository;
		this.blacklistCustomerRepository = blacklistCustomerRepository;
	}

	public List<CustomerDto> getBlacklistCustomers() {
		List<Customer> blacklistCustomers = blacklistCustomerRepository.getBlacklistCustomers();
		return blacklistCustomers.stream()
			.map(CustomerDto::from)
			.toList();
	}

	public CustomerDto createCustomer(String name, String email) {
		Customer customer = customerRepository.insert(
			new Customer(UUID.randomUUID(), name, email, LocalDateTime.now()));
		return CustomerDto.from(customer);
	}

	public List<CustomerDto> getAllCustomers() {
		List<Customer> customers = customerRepository.findAll();
		return customers.stream()
			.map(CustomerDto::from)
			.toList();
	}

	public CustomerDto getCustomerById(UUID customerId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(CUSTOMER_NOT_FOUND.getMessage()));
		return CustomerDto.from(customer);
	}

	public CustomerDto updateCustomer(UUID customerId, String nameToUpdate) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(CUSTOMER_NOT_FOUND.getMessage()));
		customer.changeName(nameToUpdate);
		customerRepository.update(customer);
		return CustomerDto.from(customer);
	}

	public CustomerDto removeCustomer(UUID customerId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(CUSTOMER_NOT_FOUND.getMessage()));
		customerRepository.deleteById(customer.getId());
		return CustomerDto.from(customer);
	}

	public void assignVoucherToCustomer(UUID customerId, UUID voucherId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(CUSTOMER_NOT_FOUND.getMessage()));
		Voucher voucher = voucherRepository.findById(voucherId)
			.orElseThrow(() -> new NoSuchElementException(VOUCHER_NOT_FOUND.getMessage()));

		walletRepository.insertVoucherForCustomer(customer.getId(), voucher.getVoucherId());
	}

	public List<VoucherDto> getVouchersByCustomer(UUID customerId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(CUSTOMER_NOT_FOUND.getMessage()));

		List<UUID> voucherIds = walletRepository.findVoucherIdsByCustomerId(customer.getId());

		List<Voucher> vouchers = voucherRepository.findAllById(voucherIds);
		return vouchers.stream()
			.map(VoucherDto::from)
			.toList();
	}

	public void removeVoucherFromCustomer(UUID customerId, UUID voucherId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(CUSTOMER_NOT_FOUND.getMessage()));
		Voucher voucher = voucherRepository.findById(voucherId)
			.orElseThrow(() -> new NoSuchElementException(VOUCHER_NOT_FOUND.getMessage()));

		walletRepository.deleteVoucherFromCustomer(customer.getId(), voucher.getVoucherId());
	}
}
