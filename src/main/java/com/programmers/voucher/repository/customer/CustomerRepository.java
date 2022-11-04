package com.programmers.voucher.repository.customer;

import java.util.List;

import com.programmers.voucher.domain.customer.Customer;

public interface CustomerRepository {

	List<Customer> findAllBlacklist();
}
