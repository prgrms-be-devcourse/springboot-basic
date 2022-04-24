package org.prgrms.kdt.repository;

import org.prgrms.kdt.model.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insertCustomer(Customer customer);

    List<Customer> findAllCustomer();

    Optional<Customer> findCustomerById(UUID customerId);

    void deleteAllCustomer();
}
