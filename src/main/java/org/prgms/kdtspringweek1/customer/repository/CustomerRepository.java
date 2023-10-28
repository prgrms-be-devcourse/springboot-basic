package org.prgms.kdtspringweek1.customer.repository;

import org.prgms.kdtspringweek1.customer.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    List<Customer> findAllCustomers();

    Optional<Customer> findById(UUID customerId);

    Customer update(Customer customer);

    void deleteAll();

    Optional<Customer> deleteById(UUID customerId);

    List<Customer> findAllBlackConsumer();
}
