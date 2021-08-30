package org.programmers.kdt.customer.service;

import org.programmers.kdt.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    Customer signUp(UUID customerId, String name, String email);

    Optional<Customer> findCustomerById(UUID customerId);
    List<Customer> findCustomersByName(String name);
    Optional<Customer> findCustomerByEmail(String email);

    Customer addToBlacklist(Customer customer);

    List<Customer> findAll();
    List<Customer> findAllBlacklistCustomer();

    default Optional<Customer> removeCustomer(Customer customer) {
        return removeCustomer(customer.getCustomerId());
    }

    Optional<Customer> removeCustomer(UUID customerId);

    Boolean isBlacklisted(Customer customer);

    String getPrintFormat(Customer customer);
}
