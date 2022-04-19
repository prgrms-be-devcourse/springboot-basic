package org.prgrms.part1.engine.repository;

import org.prgrms.part1.engine.domain.Customer;
import org.prgrms.part1.engine.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    List<Customer> findAll();

    List<Customer> findBlackStatus(Boolean isBlack);

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    List<Voucher> findOwnedVouchers(Customer customer);

    Customer insert(Customer customer);

    Customer update(Customer customer);

    void deleteAll();
}
