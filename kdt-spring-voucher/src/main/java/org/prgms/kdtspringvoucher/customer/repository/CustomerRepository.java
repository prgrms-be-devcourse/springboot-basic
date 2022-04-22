package org.prgms.kdtspringvoucher.customer.repository;

import org.prgms.kdtspringvoucher.customer.domain.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);

    Customer update(Customer customer);

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByEmail(String email);

    List<Customer> findByName(String name);

    List<Customer> findBlackList();

    List<Customer> findAll();

    void deleteAll();


}
