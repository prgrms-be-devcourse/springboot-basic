package org.programmers.customer.repository;

import org.programmers.customer.model.Customer;

import java.io.IOException;
import java.util.List;

public interface CustomerRepository {
    List<Customer> findAllBlackList() throws IOException;

    void save(Customer customer);
}
