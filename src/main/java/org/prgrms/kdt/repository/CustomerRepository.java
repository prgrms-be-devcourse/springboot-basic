package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> saveCustomer(Customer customer);
    Optional<Customer> getCustomerById(long customerId);
    Optional<Customer> getCustomerByEmail(String email);
}
