package org.prgrms.springorder.service;

import java.util.List;
import java.util.stream.Collectors;

import org.prgrms.springorder.domain.Customer;
import org.prgrms.springorder.repository.CustomerRepository;
import org.springframework.stereotype.Service;
@Service
public class CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public List<String> getBlackList() {
		return customerRepository.getBlackList().stream().map(Customer::toString).collect(Collectors.toList());
	}
}
