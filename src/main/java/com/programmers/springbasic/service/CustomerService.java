package com.programmers.springbasic.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.programmers.springbasic.dto.GetBlacklistCustomersResponse;
import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.repository.wallet.WalletJdbcRepository;
import com.programmers.springbasic.repository.customer.BlacklistCustomerRepository;
import com.programmers.springbasic.repository.customer.CustomerRepository;
import com.programmers.springbasic.repository.voucher.VoucherRepository;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final VoucherRepository voucherRepository;
	private final WalletJdbcRepository walletJdbcRepository;
	private final BlacklistCustomerRepository blacklistCustomerRepository;

	public CustomerService(CustomerRepository customerRepository,
		VoucherRepository voucherRepository, WalletJdbcRepository walletJdbcRepository,
		BlacklistCustomerRepository blacklistCustomerRepository) {
		this.customerRepository = customerRepository;
		this.voucherRepository = voucherRepository;
		this.walletJdbcRepository = walletJdbcRepository;
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
		return customerRepository.findById(customerId).orElseThrow();
	}

	public Customer updateCustomer(UUID customerId, String nameToUpdate) {
		Customer customer = customerRepository.findById(customerId).orElseThrow();
		customer.changeName(nameToUpdate);
		customerRepository.update(customer);
		return customer;
	}

	public Customer deleteCustomer(UUID customerId) {
		Customer customer = customerRepository.findById(customerId).orElseThrow();
		customerRepository.deleteById(customer.getId());
		return customer;
	}

	public void assignVoucherToCustomer(UUID customerId, UUID voucherId) {
		Customer customer = customerRepository.findById(customerId).orElseThrow();
		Voucher voucher = voucherRepository.findById(voucherId).orElseThrow();

		walletJdbcRepository.insertVoucherForCustomer(customer.getId(), voucher.getVoucherId());
	}

	public Customer getVouchersByCustomer(UUID customerId) {
		Customer customer = customerRepository.findById(customerId).orElseThrow();

		List<Voucher> vouchers = walletJdbcRepository.findVouchersByCustomerId(customer.getId());
		customer.setVouchers(vouchers);

		return customer;
	}

	public void removeVoucherFromCustomer(UUID customerId, UUID voucherId) {
		Customer customer = customerRepository.findById(customerId).orElseThrow();
		Voucher voucher = voucherRepository.findById(voucherId).orElseThrow();

		walletJdbcRepository.removeVoucherForCustomer(customer.getId(), voucher.getVoucherId());
	}

	public List<Customer> getCustomersByVoucher(UUID voucherId) {
		Voucher voucher = voucherRepository.findById(voucherId).orElseThrow();
		return walletJdbcRepository.findCustomersByVoucherId(voucher.getVoucherId());
	}
}
