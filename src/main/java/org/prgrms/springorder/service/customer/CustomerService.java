package org.prgrms.springorder.service.customer;

import java.util.List;
import java.util.stream.Collectors;

import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.repository.customer.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public List<String> getCustomerList() {
		return customerRepository.findAll().stream().map(Customer::toString).collect(Collectors.toList());
	}

	public void createCustomer(Customer customer) {
		customerRepository.save(customer);
	}
}
