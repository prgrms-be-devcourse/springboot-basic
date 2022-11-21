package org.prgms.springbootbasic.repository.customer;

import org.prgms.springbootbasic.domain.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);
    Customer update(Customer customer);

    Customer updateLastLoginAt(Customer customer);
    List<Customer> findAll();
    int count();
    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findByEmail(String email);
    UUID deleteById(UUID customerId);
    void deleteAll();

    List<Customer> findByVoucherId(UUID voucherId);
}
