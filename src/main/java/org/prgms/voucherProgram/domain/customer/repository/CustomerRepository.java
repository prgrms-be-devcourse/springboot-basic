package org.prgms.voucherProgram.domain.customer.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.prgms.voucherProgram.domain.customer.domain.Customer;

public interface CustomerRepository {
    Customer save(Customer customer);

    Customer update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByVoucherId(UUID voucherId);

    void deleteAll();

    void deleteById(UUID customerId);

    void deleteByEmail(String email);
}
