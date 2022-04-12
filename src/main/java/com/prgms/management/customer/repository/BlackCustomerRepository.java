package com.prgms.management.customer.repository;

import com.prgms.management.customer.model.Customer;

import java.util.List;

public interface BlackCustomerRepository {
    List<Customer> findAll();
}
