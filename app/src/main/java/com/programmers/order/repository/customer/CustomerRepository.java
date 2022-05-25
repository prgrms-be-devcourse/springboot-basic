package com.programmers.order.repository.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.programmers.order.domain.Customer;

public interface CustomerRepository {

	Customer insert(Customer customer);

	Customer update(Customer customer);

	List<Customer> findAll();

	Optional<Customer> findById(UUID customerId);

	Optional<Customer> findByName(String name);

	Optional<Customer> findByEmail(String email);

	int count();

	int countByEmail(String email);

	void deleteAll();
}
