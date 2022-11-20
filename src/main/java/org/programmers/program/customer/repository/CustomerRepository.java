package org.programmers.program.customer.repository;

import org.programmers.program.customer.model.Customer;

import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);
    Customer findById(UUID id);
}
