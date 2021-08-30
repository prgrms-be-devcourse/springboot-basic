package org.prgrms.kdt.kdtspringorder.custommer.repository;

import org.prgrms.kdt.kdtspringorder.custommer.domain.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    int count();

    int insert(Customer customer);

    int update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByIEmail(String email);

    int deleteAll();

}
