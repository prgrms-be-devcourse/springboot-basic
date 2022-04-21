package org.prgrms.deukyun.voucherapp.domain.customer.repository;

import org.prgrms.deukyun.voucherapp.domain.customer.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 고객 리포지토리
 */
public interface CustomerRepository {

    Customer insert(Customer customer);

    Optional<Customer> findById(UUID id);

    List<Customer> findAll();

    void clear();
}
