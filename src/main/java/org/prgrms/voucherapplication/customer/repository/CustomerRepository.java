package org.prgrms.voucherapplication.customer.repository;

import org.prgrms.voucherapplication.customer.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository {

    Customer insert(Customer customer);
    Customer update(Customer customer);
    int count();
    List<Customer> findAll();
    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
    void deleteAll();

    void delete(UUID customerId);
}
