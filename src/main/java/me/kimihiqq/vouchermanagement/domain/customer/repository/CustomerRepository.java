package me.kimihiqq.vouchermanagement.domain.customer.repository;

import me.kimihiqq.vouchermanagement.domain.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(UUID customerId);

    List<Customer> findAll();

    void deleteById(UUID customerId);

    void update(Customer customer);


}