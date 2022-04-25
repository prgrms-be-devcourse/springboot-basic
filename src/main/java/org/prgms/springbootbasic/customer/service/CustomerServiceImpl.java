package org.prgms.springbootbasic.customer.service;

import java.util.List;
import java.util.Map;

import org.prgms.springbootbasic.customer.entity.Customer;
import org.prgms.springbootbasic.customer.entity.CustomerStatus;
import org.prgms.springbootbasic.customer.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	@Override
	public void createCustomer(String name, String email) {
		final Customer customer = new Customer(name, email);
		customerRepository.save(customer);
	}

	@Override
	public Map<CustomerStatus, List<Customer>> list() {
		return customerRepository.getCustomerListByStatus();
	}

}
