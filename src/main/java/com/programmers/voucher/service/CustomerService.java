package com.programmers.voucher.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programmers.voucher.domain.customer.Customer;
import com.programmers.voucher.repository.customer.CustomerRepository;

@Service
public class CustomerService {

	private final CustomerRepository repository;

	@Autowired
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public List<Customer> getBlackList() {
		return repository.findAllBlacklist();
	}
}
