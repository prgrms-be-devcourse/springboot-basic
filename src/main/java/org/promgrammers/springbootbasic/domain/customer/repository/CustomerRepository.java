package org.promgrammers.springbootbasic.domain.customer.repository;

import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.exception.repository.NonExistentDomainException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(UUID customerId);

    default Customer getById(UUID customerId) {
        return findById(customerId)
                .orElseThrow(() -> new NonExistentDomainException("존재하지 않는 고객입니다."));
    }

    Optional<Customer> findByUsername(String username);

    List<Customer> findAll();

    Customer update(Customer customer);

    void deleteAll();
}
