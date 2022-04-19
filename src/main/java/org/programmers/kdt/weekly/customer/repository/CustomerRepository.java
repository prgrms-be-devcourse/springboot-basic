package org.programmers.kdt.weekly.customer.repository;


import java.util.List;
import org.programmers.kdt.weekly.customer.Customer;

public interface CustomerRepository {

    Customer insert(Customer customer);

    List<Customer> findAll();
}