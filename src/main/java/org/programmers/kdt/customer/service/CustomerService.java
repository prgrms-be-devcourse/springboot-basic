package org.programmers.kdt.customer.service;

import org.programmers.kdt.customer.Customer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    Customer signUp(UUID customerId, String name, String email) throws IOException;

    Optional<Customer> findCustomerById(UUID customerId);
    List<Customer> findCustomersByName(String name);
    Optional<Customer> findCustomerByEmail(String email);

    Customer registerToBlacklist(Customer customer) throws IOException;

    List<Customer> findAll();
    List<Customer> findAllBlacklistCustomer();

    Boolean isBlacklisted(Customer customer);
}
