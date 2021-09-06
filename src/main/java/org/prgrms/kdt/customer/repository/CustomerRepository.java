package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);
    Customer update(Customer customer);

    // insert + update, jpa에서 지원해줌
    // Customer save(Customer customer);

    int count();

    List<Customer> findAll();

    // Optional -> null 처리를 고민할 필요가 없음
    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);

    void deleteAll();
}
