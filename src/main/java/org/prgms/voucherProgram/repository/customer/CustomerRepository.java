package org.prgms.voucherProgram.repository.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.prgms.voucherProgram.entity.customer.Customer;

public interface CustomerRepository {
    Customer save(Customer customer);

    Customer update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByEmail(String email);

    void deleteAll();

    void deleteById(UUID customerId);

    void deleteByEmail(String email);
}
