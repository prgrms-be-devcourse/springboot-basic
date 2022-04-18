package org.prgrms.springbasic.repository.customer;

import org.prgrms.springbasic.domain.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(UUID customerId);

    List<Customer> findAll();

    int countStorageSize();

    Customer updateCustomer(Customer customer);

    void clear();
}
