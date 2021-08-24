package org.prgrms.kdtspringdemo.customer;

import org.prgrms.kdtspringdemo.voucher.Voucher;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface CustomerRepository {
    Optional<Customer> findById(UUID customerId);
    Customer insert(Customer customer);

    Stream<Customer> findAll();

    Stream<Customer> findBlacklist();
}
