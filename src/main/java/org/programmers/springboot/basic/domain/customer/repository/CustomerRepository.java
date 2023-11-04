package org.programmers.springboot.basic.domain.customer.repository;

import org.programmers.springboot.basic.domain.customer.entity.Customer;
import org.programmers.springboot.basic.domain.customer.entity.vo.Email;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    void save(Customer customer);
    Optional<Customer> findByEmail(Email email);
    Optional<Customer> findById(UUID customerId);
    List<Customer> findAll();
    List<Customer> findBlack();
    void update(Customer customer);
    void delete(Customer customer);
    void deleteAll();
}
