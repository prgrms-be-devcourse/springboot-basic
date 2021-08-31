package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.CustomerEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    CustomerEntity insert(CustomerEntity customer);

    CustomerEntity update(CustomerEntity customer);

    List<CustomerEntity> findAll();

    void deleteAll();

    void deleteById(UUID customerId);

    Optional<CustomerEntity> findById(UUID customerId);

    Optional<CustomerEntity> findByName(String name);

    Optional<CustomerEntity> findByEmail(String email);
}