package com.programmers.springbasic.repository.customer;

import java.util.List;
import java.util.Optional;

import com.programmers.springbasic.entity.customer.Customer;

public interface CustomerRepository {

	Customer save(Customer customer);

	List<Customer> findAll();

	Optional<Customer> findById(Long id);

	List<Customer> findAllByIsBlackListedTrue();

	void deleteById(Long id);

}
