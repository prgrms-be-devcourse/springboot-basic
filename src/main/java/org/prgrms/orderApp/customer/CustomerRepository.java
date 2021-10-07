package org.prgrms.orderApp.customer;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



public interface CustomerRepository {

    List<Customer> findAll() ;
    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String Email);
    int count();

    Customer insert(Customer customer);

    Customer update(Customer customer);

    void deleteAll();


}
