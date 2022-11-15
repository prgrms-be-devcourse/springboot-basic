package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);
    void update(Customer customer);
    List<Customer> findAll();
    Optional<Customer> findById(UUID targetId);
    void deleteById(UUID targetId);
    void deleteAll();
    List<Customer> findAllBlackList();

}
