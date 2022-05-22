package org.prgms.voucheradmin.domain.customer.dao.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.prgms.voucheradmin.domain.customer.entity.Customer;

public interface CustomerRepository {
    Customer create(Customer customer);

    List<Customer> findAll();

    List<Customer> findVoucherOwners(UUID voucherId);

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByEmail(String email);

    Customer update(Customer customer);

    void delete(Customer customer);
}
