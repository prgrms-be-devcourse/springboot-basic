package org.prgrms.vouchermanager.repository.customer;

import org.prgrms.vouchermanager.domain.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepositroy {
    List<Customer> findAll();

    Customer save(Customer customer);

    Optional<Customer> findById(UUID customerId);
    Optional<Customer> deleteById(UUID customerId);
}
