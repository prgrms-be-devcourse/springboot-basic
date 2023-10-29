package com.programmers.springbasic.repository.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.programmers.springbasic.entity.customer.Customer;

public interface CustomerRepository {

	Customer insert(Customer customer);

	Customer update(Customer customer);

	List<Customer> findAll();

	Optional<Customer> findById(UUID id);

	void deleteById(UUID id);

	List<Customer> findAllById(List<UUID> customerIds);
}
