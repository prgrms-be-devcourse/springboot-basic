package org.prgrms.voucherapplication.repository.customer.jdbc;

import org.prgrms.voucherapplication.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 고객 데이터를 관리하는 레포지포리
 */
public interface CustomerRepository {

    List<Customer> findAll();

    Customer insert(Customer customer);

    Customer update(Customer customer);

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    void deleteById(UUID customerId);

    void deleteAll();
}
