package com.programmers.voucher.domain.customer.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.customer.model.CustomerType;
import com.programmers.voucher.domain.customer.repository.CustomerRepository;

@Service
public class CustomerService {

	private final CustomerRepository repository;

	@Autowired
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public Customer createCustomer(CustomerType customerType) {
		Customer customer = new Customer(UUID.randomUUID(), LocalDateTime.now(), customerType, LocalDateTime.now());
		repository.save(customer);
		return customer;
	}

	public List<Customer> getAllCustomer() {
		return repository.findAll();
	}

	public List<Customer> getBlackList() {
		return repository.findAllBlacklist();
	}
}
