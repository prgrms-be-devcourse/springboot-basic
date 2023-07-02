package com.programmers.customer.service;

import com.programmers.customer.domain.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    Customer create(Customer customer);

    List<Customer> findCustomers();

    Customer findCustomerById(UUID customerID);

    void deleteCustomerById(UUID customerID);
}
