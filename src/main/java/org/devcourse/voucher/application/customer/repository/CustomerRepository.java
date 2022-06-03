package org.devcourse.voucher.application.customer.repository;

import org.devcourse.voucher.application.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);

    Page<Customer> findAll(Pageable pageable);

    Optional<Customer> findById(UUID customerId);

    Customer update(Customer customer);

    void deleteAll();

    void deleteById(UUID customerId);
}
