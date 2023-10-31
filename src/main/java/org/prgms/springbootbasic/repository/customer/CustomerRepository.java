package org.prgms.springbootbasic.repository.customer;

import org.prgms.springbootbasic.domain.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer upsert(Customer customer);
    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findByEmail(String email);
    List<Customer> findAll();
    List<Customer> findBlackAll();
    void deleteById(UUID customerId);
    int deleteAll();
}
