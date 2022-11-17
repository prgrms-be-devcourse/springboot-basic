package org.prgrms.springorder.domain.customer.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springorder.domain.customer.Wallet;
import org.prgrms.springorder.domain.customer.model.Customer;

public interface CustomerRepository {

    Optional<Customer> findById(UUID customerId);

    Customer insert(Customer customer);

    List<Customer> findAll();

    void deleteAll();

    Customer update(Customer customer);

    Optional<Wallet> findByIdWithVouchers(UUID customerId);
}
