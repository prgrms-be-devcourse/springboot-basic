package org.prgrms.orderapp.repository;

import org.prgrms.orderapp.model.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> getBlacklist();
    Customer save(Customer customer);
    int size();
}
