package org.promgrammers.voucher.repository;

import org.promgrammers.voucher.domain.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(UUID customerId);

    List<Customer> findAll();

    Customer update(Customer customer);

    void deleteAll();


}
