package com.programmers.customer.black;

import com.programmers.customer.Customer;

import java.util.List;

public interface BlackCustomerRepository {
    List<Customer> findAllBlackList();
}
