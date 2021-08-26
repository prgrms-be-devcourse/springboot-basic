package org.programmers.kdt.customer.repository;

import org.programmers.kdt.customer.Customer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer) throws IOException;
    default Optional<Customer> deleteCustomer(Customer customer) {
        return deleteCustomer(customer.getCustomerId());
    }
    Optional<Customer> deleteCustomer(UUID customerId);

    Optional<Customer> findById(UUID customerId);
    List<Customer> findByName(String name);
    Optional<Customer> fineByEmail(String email);

    List<Customer> findAll();

    Customer registerToBlacklist(Customer customer) throws IOException;
    List<Customer> findAllBlacklistCustomer();
}
