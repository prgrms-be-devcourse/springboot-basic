package com.programmers.customer.service;

import com.programmers.customer.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    void createCustomer(String email, String password, String name);

    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerByEmail(String email);

    void updateByName(Customer customer);

    void deleteAll();
}
