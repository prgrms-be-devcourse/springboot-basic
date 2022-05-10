package com.example.voucher.service.customer;

import com.example.voucher.domain.customer.Customer;
import java.util.List;

public interface CustomerService {
	List<Customer> findAll();
	Customer save(String name);
	void deleteById(Long customerId);
}
