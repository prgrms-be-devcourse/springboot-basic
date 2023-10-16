package com.programmers.springbasic.repository;

import java.util.List;

import com.programmers.springbasic.entity.Customer;

public interface CustomerRepository {

	List<Customer> findAllByIsBlackListedTrue();
}
