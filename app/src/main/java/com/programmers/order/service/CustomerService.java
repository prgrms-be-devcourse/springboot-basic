package com.programmers.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.order.controller.dto.CustomerDto;
import com.programmers.order.domain.Customer;
import com.programmers.order.repository.CustomerRepository;

@Transactional
@Service
public class CustomerService {
	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Customer create(Customer customer) {
		return customerRepository.insert(customer);
	}
}
