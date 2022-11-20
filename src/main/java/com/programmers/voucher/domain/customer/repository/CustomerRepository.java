package com.programmers.voucher.domain.customer.repository;

import java.util.List;
import java.util.UUID;

import com.programmers.voucher.domain.customer.model.Customer;

public interface CustomerRepository {

	Customer save(Customer customer);

	Customer findById(UUID customerId);

	Customer update(Customer updateCustomer);

	void delete(UUID customerId);

	List<Customer> findAll();

	List<Customer> findAllBlacklist();

	void clear();
}
