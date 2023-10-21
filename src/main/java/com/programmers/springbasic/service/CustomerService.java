package com.programmers.springbasic.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.programmers.springbasic.dto.GetBlacklistCustomersResponse;
import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.repository.customer.CustomerRepository;

@Service
public class CustomerService {
	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public List<GetBlacklistCustomersResponse> getBlacklistCustomers() {
		List<Customer> blacklistCustomers = customerRepository.findAllByIsBlackListedTrue();
		return blacklistCustomers.stream()
			.map(GetBlacklistCustomersResponse::new)
			.collect(Collectors.toList());
	}
}
