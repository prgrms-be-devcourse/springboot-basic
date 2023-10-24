package com.pgms.part1.domain.customer.repository;

import com.pgms.part1.domain.customer.entity.Customer;

import java.util.List;

public interface CustomerRepository {
    public List<Customer> listBlockedCustomers();
    public List<Customer> listCustomers();
    public void addCustomer(Customer customer);
    public void updateCustomerName(Customer customer, String name);
    public void deleteCustomer(Customer customer);
}
