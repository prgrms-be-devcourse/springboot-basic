package com.prgrms.vouchermanagement.customer.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.vouchermanagement.commons.exception.AlreadyExistException;
import com.prgrms.vouchermanagement.commons.exception.NotExistException;
import com.prgrms.vouchermanagement.customer.domain.Customer;
import com.prgrms.vouchermanagement.customer.repository.CustomerRepository;

@Service
public class CustomerService {
	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Transactional
	public Customer join(String name, String email) {
		checkBlankString(name, "name 에는 빈 칸이 올 수 없습니다");
		checkBlankString(email, "email 에는 빈 칸이 올 수 없습니다");
		checkDuplicatedEmail(email);

		UUID id = UUID.randomUUID();

		checkDuplicatedId(id);

		Customer customer = new Customer(id, name, email);

		return customerRepository.insert(customer);
	}

	@Transactional(readOnly = true)
	protected void checkDuplicatedEmail(String email) {
		if (customerRepository.findByEmail(email)
			.isPresent()) {
			throw new AlreadyExistException();
		}
	}

	@Transactional(readOnly = true)
	protected void checkDuplicatedId(UUID id) {
		if (customerRepository.findById(id)
			.isPresent()) {
			throw new AlreadyExistException();
		}
	}

	private void checkBlankString(String str, String message) {
		if (str.isBlank()) {
			throw new IllegalArgumentException(message);
		}
	}

	@Transactional
	public Customer updateName(UUID id, String name) {
		checkBlankString(name, "name 에는 빈 칸이 올 수 없습니다");

		Customer customer = customerRepository.findById(id)
			.orElseThrow(NotExistException::new);

		customer.changeName(name);
		customerRepository.update(customer);

		return customer;
	}

	public Customer getById(UUID id) {
		return customerRepository.findById(id)
			.orElseThrow(NotExistException::new);
	}

	public Customer getByName(String name) {
		return customerRepository.findByName(name)
			.orElseThrow(NotExistException::new);
	}

	public Customer getByEmail(String email) {
		return customerRepository.findByEmail(email)
			.orElseThrow(NotExistException::new);
	}

	public void resignById(UUID id) {
		customerRepository.deleteById(id);
	}

}
