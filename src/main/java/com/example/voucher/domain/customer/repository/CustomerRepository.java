package com.example.voucher.domain.customer.repository;

import com.example.voucher.domain.customer.Customer;
import java.util.List;

public interface CustomerRepository {
	List<Customer> findAll();
	Customer save(Customer customer);
	int deleteById(Long customerId);
}
