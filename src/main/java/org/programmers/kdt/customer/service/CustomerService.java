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

    default void removeCustomer(Customer customer) {
        removeCustomer(customer.getCustomerId());
    }

    void removeCustomer(UUID customerId);

    boolean isOnBlacklist(Customer customer);

    String getPrintFormat(Customer customer);
}
