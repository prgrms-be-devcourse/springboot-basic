package org.promgrammers.voucher.repository;

import org.promgrammers.voucher.domain.Customer;
import org.promgrammers.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(UUID id);

    List<Customer> findAll();

    Customer update(Customer customer);

    void deleteAll();


    Optional<Customer> findByUsername(String username);

    Optional<Customer> findByVoucherId(UUID voucherId);

    void deleteById(UUID customerId);

    void assignVoucherToCustomer(UUID customerId, Voucher voucher);
}
