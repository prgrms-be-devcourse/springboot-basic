package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    List<Voucher> findHaveVouchersById(UUID customerId);

    List<Customer> findBlackList();

    Customer save(Customer customer);

    Customer update(Customer customer);

    int count();

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    void deleteAll();
}
