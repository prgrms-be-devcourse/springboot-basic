package org.programmers.kdt.weekly.customer.repository;


import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.programmers.kdt.weekly.customer.Customer;

public interface CustomerRepository {

    Customer insert(Customer customer);

    List<Customer> findAll();
}
