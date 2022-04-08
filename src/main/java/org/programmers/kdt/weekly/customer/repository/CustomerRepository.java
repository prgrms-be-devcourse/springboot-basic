package org.programmers.kdt.weekly.customer.repository;


import org.programmers.kdt.weekly.customer.Customer;

import java.util.UUID;

public interface CustomerRepository {
    void insert(UUID customerId, Customer customer);

    int getSize();

    void showAll();
}
