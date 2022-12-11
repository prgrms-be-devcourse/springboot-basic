package com.programmers.voucher.domain.customer.repository;

import java.util.List;

import com.programmers.voucher.domain.customer.model.Customer;

public interface CustomerRepository {

	List<Customer> findAllBlacklist();
}
