package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.model.Customer;

import java.util.List;

public interface CustomerRepository {
    void insert(Customer customer);
    List<Customer> getCustomerList();
}
