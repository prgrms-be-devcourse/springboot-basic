package org.prgms.w3d1.repository;

import org.prgms.w3d1.model.customer.Customer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer insert(Customer customer);
    Customer update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    List<Customer> findByCreatedDateRange(LocalDateTime startDate, LocalDateTime endDate);

    int count();

    void deleteAll();
}
