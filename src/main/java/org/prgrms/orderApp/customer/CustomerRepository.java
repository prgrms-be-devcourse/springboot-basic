package org.prgrms.orderApp.customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository {
    List<Customer> findAll() ;
    Customer insert(Customer customer);
    Customer update(Customer customer);
    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String Email);
    void deleteAll();
    int count();

}
