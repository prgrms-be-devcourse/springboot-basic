package com.programmers.customer.service;

import com.programmers.customer.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    Customer join(String name, String email);

    Customer findById(UUID customerId);

    Customer findByName(String name);

    Customer findByEmail(String email);

    Customer findCustomerByVoucherId(UUID voucherId);

    Customer update(Customer customer);

    List<Customer> findAll();

    void deleteCustomer(UUID customerId);
}

