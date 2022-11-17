package com.example.springbootbasic.repository.customer;

import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.domain.customer.CustomerStatus;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAllCustomers();
    List<Customer> findCustomersByStatus(CustomerStatus status);
}
