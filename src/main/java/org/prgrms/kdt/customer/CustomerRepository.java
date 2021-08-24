package org.prgrms.kdt.customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findById(UUID id);

    Optional<Customer> findByUserId(String customerId);

    List<Customer> findAll();

    List<Customer> getBlackList();
}
