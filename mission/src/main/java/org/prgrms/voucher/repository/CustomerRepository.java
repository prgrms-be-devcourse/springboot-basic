package org.prgrms.voucher.repository;

import org.prgrms.voucher.models.Customer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Customer save(Customer customer);

    List<Customer> findAll();

    List<Customer> findByTerm(LocalDate after, LocalDate before);

    Optional<Customer> findById(Long customerId);

    void deleteById(Long customerId);
}
