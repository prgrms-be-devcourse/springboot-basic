package org.prgrms.prgrmsspring.repository.user;

import org.prgrms.prgrmsspring.entity.user.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository {
    List<Customer> findAll();

    List<Customer> findBlackAll();

    Optional<Customer> findById(UUID customerId);

    Customer insert(Customer customer);

    Customer update(Customer customer);

    void delete(UUID customerId);
}
