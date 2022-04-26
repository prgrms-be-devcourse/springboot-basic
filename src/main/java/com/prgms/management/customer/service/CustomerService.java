package com.prgms.management.customer.service;

import com.prgms.management.customer.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> findAllCustomers();

    Customer findCustomerById(UUID id);

    Customer addCustomer(Customer customer);

    void removeCustomerById(UUID id);
}
