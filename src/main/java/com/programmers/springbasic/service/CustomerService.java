package com.programmers.springbasic.service;

import static com.programmers.springbasic.enums.ErrorCode.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.programmers.springbasic.dto.GetBlacklistCustomersResponse;
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

	public List<GetBlacklistCustomersResponse> getBlacklistCustomers() {
		List<Customer> blacklistCustomers = blacklistCustomerRepository.getBlacklistCustomers();
		return blacklistCustomers.stream()
			.map(GetBlacklistCustomersResponse::new)
			.collect(Collectors.toList());
	}

	public Customer createCustomer(String name, String email) {
		return customerRepository.insert(new Customer(UUID.randomUUID(), name, email, LocalDateTime.now()));
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public Customer getCustomerById(UUID customerId) {
		return customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(CUSTOMER_NOT_FOUND.getMessage()));
	}

	public Customer updateCustomer(UUID customerId, String nameToUpdate) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(CUSTOMER_NOT_FOUND.getMessage()));
		customer.changeName(nameToUpdate);
		customerRepository.update(customer);
		return customer;
	}

	public Customer deleteCustomer(UUID customerId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(CUSTOMER_NOT_FOUND.getMessage()));
		customerRepository.deleteById(customer.getId());
		return customer;
	}

	public void assignVoucherToCustomer(UUID customerId, UUID voucherId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(CUSTOMER_NOT_FOUND.getMessage()));
		Voucher voucher = voucherRepository.findById(voucherId)
			.orElseThrow(() -> new NoSuchElementException(VOUCHER_NOT_FOUND.getMessage()));

		walletRepository.insertVoucherForCustomer(customer.getId(), voucher.getVoucherId());
	}

	public Customer getVouchersByCustomer(UUID customerId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(CUSTOMER_NOT_FOUND.getMessage()));

		List<Voucher> vouchers = walletRepository.findVouchersByCustomerId(customer.getId());
		customer.setVouchers(vouchers);

		return customer;
	}

	public void removeVoucherFromCustomer(UUID customerId, UUID voucherId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(CUSTOMER_NOT_FOUND.getMessage()));
		Voucher voucher = voucherRepository.findById(voucherId)
			.orElseThrow(() -> new NoSuchElementException(VOUCHER_NOT_FOUND.getMessage()));

		walletRepository.removeVoucherForCustomer(customer.getId(), voucher.getVoucherId());
	}
}
