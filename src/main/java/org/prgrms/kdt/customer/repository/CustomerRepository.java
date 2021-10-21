package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.Customer;

import java.util.List;

public interface CustomerRepository {
    Customer insert(Customer customer);

    List<Customer> findAllCustomer();

    List<Customer> findAllBlacklist();
}
