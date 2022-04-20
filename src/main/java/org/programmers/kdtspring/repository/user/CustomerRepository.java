package org.programmers.kdtspring.repository.user;

import org.programmers.kdtspring.entity.user.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Customer save(Customer customer);

    Customer update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(Long customerId);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    void deleteAll();

    int count();
}
