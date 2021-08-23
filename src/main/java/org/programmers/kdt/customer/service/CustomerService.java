package org.programmers.kdt.customer.service;

import org.programmers.kdt.customer.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    Customer join(UUID uuid, String name, String email);

    Customer findCustomerById(UUID customerId);
    List<Customer> findCustomersByName(String name);
    List<Customer> findCustomersByEmail(String email);

    Customer registerToBlacklist(Customer customer);

    List<Customer> getBlacklistCustomers();
    List<Customer> getAllCustomer();

    Boolean isOnBlacklist(Customer customer);
}
