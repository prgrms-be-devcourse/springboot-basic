package com.programmers.springbasic.repository.customer;

import java.util.List;

import com.programmers.springbasic.entity.customer.Customer;

public interface CustomerRepository {

	List<Customer> findAllByIsBlackListedTrue();
}
