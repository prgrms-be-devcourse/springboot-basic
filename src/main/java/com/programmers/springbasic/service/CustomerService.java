package com.programmers.springbasic.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.programmers.springbasic.controller.dto.ListBlacklistCustomerResponse;
import com.programmers.springbasic.entity.Customer;
import com.programmers.springbasic.repository.CustomerRepository;

@Service
public class CustomerService {
	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public List<ListBlacklistCustomerResponse> listBlacklistCustomer() {
		List<Customer> blacklistCustomers = customerRepository.findAllByIsBlackListedTrue();
		return blacklistCustomers.stream()
			.map(ListBlacklistCustomerResponse::new)
			.collect(Collectors.toList());
	}
}
