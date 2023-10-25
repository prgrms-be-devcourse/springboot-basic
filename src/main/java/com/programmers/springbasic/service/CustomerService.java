package com.programmers.springbasic.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.programmers.springbasic.dto.GetBlacklistCustomersResponse;
import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.repository.customer.BlacklistCustomerRepository;
import com.programmers.springbasic.repository.customer.CustomerRepository;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final BlacklistCustomerRepository blacklistCustomerRepository;

	public CustomerService(CustomerRepository customerRepository,
		BlacklistCustomerRepository blacklistCustomerRepository) {
		this.customerRepository = customerRepository;
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

	public Customer updateCustomer(String customerId, String nameToUpdate) {
		Customer customer = customerRepository.findById(UUID.fromString(customerId)).orElseThrow();
		customer.changeName(nameToUpdate);
		customerRepository.update(customer);
		return customer;
	}

	public Customer deleteCustomer(String customerId) {
		Customer customer = customerRepository.findById(UUID.fromString(customerId)).orElseThrow();
		customerRepository.deleteById(customer.getId());
		return customer;
	}
}
