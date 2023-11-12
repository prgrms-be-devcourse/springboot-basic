package com.programmers.springbasic.service;

import static com.programmers.springbasic.constants.ErrorCode.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.entity.wallet.Wallet;
import com.programmers.springbasic.repository.customer.BlacklistCustomerRepository;
import com.programmers.springbasic.repository.customer.CustomerRepository;
import com.programmers.springbasic.repository.dto.customer.CustomerResponse;
import com.programmers.springbasic.repository.dto.voucher.VoucherResponse;
import com.programmers.springbasic.repository.voucher.VoucherRepository;
import com.programmers.springbasic.repository.wallet.WalletRepository;

@Transactional
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

	public List<CustomerResponse> getBlacklistCustomers() {
		List<Customer> blacklistCustomers = blacklistCustomerRepository.getBlacklistCustomers();
		return blacklistCustomers.stream()
			.map(CustomerResponse::from)
			.toList();
	}

	public CustomerResponse createCustomer(String name, String email) {
		Customer customer = customerRepository.insert(
			new Customer(UUID.randomUUID(), name, email, LocalDateTime.now()));
		return CustomerResponse.from(customer);
	}

	public List<CustomerResponse> getAllCustomers() {
		List<Customer> customers = customerRepository.findAll();
		return customers.stream()
			.map(CustomerResponse::from)
			.toList();
	}

	public CustomerResponse getCustomerById(UUID customerId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(CUSTOMER_NOT_FOUND.getMessage()));
		return CustomerResponse.from(customer);
	}

	public CustomerResponse updateCustomer(UUID customerId, String nameToUpdate) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(CUSTOMER_NOT_FOUND.getMessage()));
		customer.changeName(nameToUpdate);
		customerRepository.update(customer);
		return CustomerResponse.from(customer);
	}

	public CustomerResponse removeCustomer(UUID customerId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(CUSTOMER_NOT_FOUND.getMessage()));
		customerRepository.deleteById(customer.getId());
		return CustomerResponse.from(customer);
	}

	public void assignVoucherToCustomer(UUID customerId, UUID voucherId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(CUSTOMER_NOT_FOUND.getMessage()));
		Voucher voucher = voucherRepository.findById(voucherId)
			.orElseThrow(() -> new NoSuchElementException(VOUCHER_NOT_FOUND.getMessage()));

		walletRepository.insertVoucherForCustomer(customer.getId(), voucher.getVoucherId());
	}

	public List<VoucherResponse> getVouchersByCustomer(UUID customerId) {
		List<Wallet> wallets = walletRepository.findByCustomerId(customerId);

		return wallets.stream()
			.map(wallet -> voucherRepository.findById(wallet.voucherId()).orElse(null))
			.filter(Objects::nonNull)
			.map(VoucherResponse::from)
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
