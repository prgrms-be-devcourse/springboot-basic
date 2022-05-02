package org.prgms.kdtspringvoucher.customer.repository;

import org.prgms.kdtspringvoucher.customer.domain.Customer;
import org.prgms.kdtspringvoucher.customer.domain.CustomerType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);

    Customer update(Customer customer);

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByEmail(String email);

    List<Customer> findByCustomerType(CustomerType customerType);

    List<Customer> findAll();

    void deleteAll();

    void deleteById(UUID customerId);
}
